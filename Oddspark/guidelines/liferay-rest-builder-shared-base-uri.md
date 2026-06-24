# Hướng dẫn: Chia sẻ Base URI giữa nhiều module trong Liferay REST Builder

## Mục tiêu

Khi có nhiều module OSGi riêng biệt (ví dụ `cms-notification` và `cms-important-notification`), muốn tất cả API hiển thị dưới **một base URI duy nhất** (ví dụ `headless-api`) trên Swagger UI tại `o/api`.

**Kết quả mong muốn:**

```
o/headless-api/v1.0/cms-regular-notification       ← module cms-notification
o/headless-api/v1.0/cms-important-notification     ← module cms-important-notification
```

---

## Tại sao không thể dùng chung `osgi.jaxrs.application.base`?

Liferay REST Builder tạo ra một **JAX-RS Application** riêng cho mỗi module, đăng ký qua OSGi property `osgi.jaxrs.application.base`. Nếu hai module cùng dùng một giá trị (ví dụ `/headless-api`), chỉ **một** trong hai được đăng ký thành công — module còn lại bị bỏ qua hoặc conflict.

---

## Giải pháp: Module B join vào JAX-RS Application của Module A

Module "guest" (B) cấu hình resource của mình để **join vào JAX-RS Application của module host (A)** thay vì tự tạo Application riêng.

---

## Các thành phần liên quan

| File | Vai trò |
|---|---|
| `CmsXxxApplication.java` | Đăng ký JAX-RS Application với `osgi.jaxrs.application.base` |
| `notification.properties` | Liên kết resource với Application qua `osgi.jaxrs.application.select` |
| `openapi.properties` | Liên kết OpenAPI resource với Application |
| `OpenAPIResourceImpl.java` | Serve OpenAPI spec, chứa `_resourceClasses` để Vulcan generate Swagger |
| `BaseNotificationResourceImpl.java` | Chứa `@Path` annotation xác định URL endpoint |

---

## Các bước thực hiện

### Bước 1 — Tắt JAX-RS Application của Module B

Module B không còn là một ứng dụng JAX-RS độc lập. Bỏ `service = Application.class` trong component của nó.

**File:** `cms-important-notification-impl/.../CmsImportantNotificationApplication.java`

```java
// TRƯỚC
@Component(
    property = {
        "liferay.jackson=false",
        "osgi.jaxrs.application.base=/cms-important-notification",
        "osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan)",
        "osgi.jaxrs.name=CmsImportantNotification"
    },
    service = Application.class   // <-- XÓA DÒNG NÀY
)

// SAU
@Component(
    property = {
        "liferay.jackson=false",
        "osgi.jaxrs.application.base=/cms-important-notification",
        "osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan)",
        "osgi.jaxrs.name=CmsImportantNotification"
    }
    // Không có service = Application.class
)
```

> Lưu ý: File này có hash `LIFERAY-REST-BUILDER-HASH` ở cuối. Nếu chạy lại `buildREST` mà schema không thay đổi, file sẽ không bị ghi đè.

---

### Bước 2 — Chuyển resource của Module B sang Application của Module A

**File:** `cms-important-notification-impl/src/main/resources/OSGI-INF/liferay/rest/v1_0/notification.properties`

```properties
# TRƯỚC
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)

# SAU — trỏ sang Application của module A
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsNotification)
```

Từ bước này, resource của Module B sẽ được serve bởi JAX-RS Application của Module A (`/headless-api`).

---

### Bước 3 — Tắt OpenAPI resource của Module B

Module B không được tự serve spec Swagger riêng (sẽ conflict với Module A).

**File:** `cms-important-notification-impl/src/main/resources/OSGI-INF/liferay/rest/v1_0/openapi.properties`

```properties
# TRƯỚC
openapi.resource=true
openapi.resource.path=/cms-important-notification
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)

# SAU — tắt và giữ nguyên application select về app đã dead
openapi.resource=false
openapi.resource.path=/cms-important-notification
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)
```

> **Quan trọng:** Giữ `osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)` (app đã dead), KHÔNG đổi sang `CmsNotification`. Nếu đổi sang `CmsNotification`, hai `OpenAPIResourceImpl` sẽ cùng join một Application, conflict nhau, khiến chỉ một spec được serve.

---

### Bước 4 — Tạo Spec class trong Module A

Liferay's Vulcan generate Swagger spec từ tập `_resourceClasses` trong `OpenAPIResourceImpl`. Vì `BaseNotificationResourceImpl` của Module B nằm trong `internal` package (không export), Module A không thể import trực tiếp.

Giải pháp: Tạo một **"spec shim class"** trong Module A, chứa đúng `@Path` annotations nhưng không có implementation thực. Vulcan đọc class này để generate spec cho endpoint của Module B.

**File mới:** `cms-notification-impl/.../ImportantNoticeV2Spec.java`

```java
package cms.notification.internal.resource.v1_0;

import cms.important.notification.dto.v1_0.ImportantNotificationResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/v1.0")
public class ImportantNoticeV2Spec {

    @GET
    @io.swagger.v3.oas.annotations.Operation(
        description = "Get important notifications by page code"
    )
    @io.swagger.v3.oas.annotations.Parameters(
        value = {
            @io.swagger.v3.oas.annotations.Parameter(
                in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
                name = "pagecode"
            )
        }
    )
    @io.swagger.v3.oas.annotations.tags.Tags(
        value = {
            @io.swagger.v3.oas.annotations.tags.Tag(name = "Notification")
        }
    )
    @Path("/cms-important-notification")
    @Produces({"application/json", "application/xml"})
    public ImportantNotificationResponse getImportantNotification(
        @io.swagger.v3.oas.annotations.Parameter(hidden = true)
        @QueryParam("pagecode") Integer pagecode) {

        return null; // Chỉ dùng cho Swagger doc, không bao giờ được gọi trực tiếp
    }

}
```

> **Lưu ý `@Parameter(hidden = true)`:** Phải thêm annotation này vào method parameter. Nếu thiếu, Vulcan sẽ đọc parameter từ **2 nguồn** cùng lúc — `@Parameters(...)` ở trên method và `@QueryParam(...)` trên argument — khiến Swagger hiển thị **2 `pagecode`** trùng nhau cho cùng một endpoint.

> Class này dùng DTO từ `cms-important-notification-api` — cần thêm dependency ở bước 5.

---

### Bước 5 — Thêm dependency vào build.gradle của Module A

Module A cần import API của Module B để dùng DTO trong spec class.

**File:** `cms-notification-impl/build.gradle`

```groovy
dependencies {
    api project(":modules:cms-notification:cms-notification-api")

    compileOnly group: "com.liferay.portal", name: "release.dxp.api"

    // Thêm dòng này
    compileOnly project(":modules:cms-important-notification:cms-important-notification-api")
}
```

---

### Bước 6 — Đăng ký Spec class vào OpenAPIResourceImpl của Module A

**File:** `cms-notification-impl/.../OpenAPIResourceImpl.java`

```java
private final Set<Class<?>> _resourceClasses = new HashSet<Class<?>>() {
    {
        add(NotificationResourceImpl.class);   // endpoint của module A
        add(OpenAPIResourceImpl.class);        // openapi endpoint

        add(ImportantNoticeV2Spec.class);      // spec cho endpoint của module B
    }
};
```

---

### Bước 7 — Build và Deploy

```bash
blade gw :modules:cms-notification:cms-notification-impl:deploy \
         :modules:cms-important-notification:cms-important-notification-impl:deploy
```

---

## Kiểm tra

Gọi API lấy spec để xác nhận cả 2 endpoint xuất hiện:

```bash
curl -s "http://localhost:8080/o/headless-api/v1.0/openapi.json" \
     -u "test@liferay.com:yourpassword" \
     | grep -o '"\/[^"]*"' | sort -u
```

Kết quả mong đợi:

```
"/v1.0/cms-important-notification"
"/v1.0/cms-regular-notification"
"/v1.0/openapi.{type}"
```

Và trên Swagger UI tại `http://localhost:8080/o/api` → chọn **CmsNotification** → thấy đủ 2 endpoint.

---

## Lưu ý quan trọng khi chạy lại `buildREST`

| File bị ảnh hưởng | Cần làm sau buildREST |
|---|---|
| `CmsImportantNotificationApplication.java` | Bỏ lại `service = Application.class` |
| `BaseNotificationResourceImpl.java` (Module B) | Kiểm tra `@Path` vẫn là `/cms-important-notification` |
| `notification.properties` (Module B) | **Không bị ảnh hưởng** (nằm trong resources, không bị buildREST ghi đè) |
| `openapi.properties` (Module B) | **Không bị ảnh hưởng** |
| `OpenAPIResourceImpl.java` (Module A) | Thêm lại `ImportantNoticeV2Spec.class` vào `_resourceClasses` |

---

## Lỗi thường gặp

### 1. Swagger hiển thị 2 parameter trùng nhau (ví dụ 2 `pagecode`)

**Nguyên nhân:** Trong spec class, Vulcan đọc parameter từ 2 nguồn độc lập:
- `@io.swagger.v3.oas.annotations.Parameters(...)` — annotation mô tả parameter visible
- `@QueryParam("...")` trên method argument — JAX-RS annotation, Vulcan cũng tự pick up

**Triệu chứng:** Endpoint trên Swagger UI có 2 input field cùng tên.

**Fix:** Thêm `@Parameter(hidden = true)` vào method argument để ẩn nguồn số 2:

```java
// SAI — sinh ra 2 pagecode
public Response getXxx(@QueryParam("pagecode") Integer pagecode) { ... }

// ĐÚNG — chỉ hiển thị 1 pagecode (từ @Parameters ở trên method)
public Response getXxx(
    @io.swagger.v3.oas.annotations.Parameter(hidden = true)
    @QueryParam("pagecode") Integer pagecode) { ... }
```

---

### 2. Swagger chỉ hiển thị endpoint của Module B, mất endpoint của Module A

**Nguyên nhân:** `openapi.properties` của Module B được đổi `osgi.jaxrs.application.select` sang `CmsNotification`, khiến `OpenAPIResourceImpl` của Module B join vào Application của Module A. Hai `OpenAPIResourceImpl` cùng `@Path("/v1.0/openapi.json")` trong một Application → conflict → chỉ một spec được serve.

**Fix:** Giữ `openapi.properties` của Module B trỏ về application cũ đã dead (`CmsImportantNotification`), đồng thời đặt `openapi.resource=false`:

```properties
openapi.resource=false
openapi.resource.path=/cms-important-notification
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)  # KHÔNG đổi sang CmsNotification
```

---

### 3. Endpoint của Module B không accessible (404) dù đã join Application

**Nguyên nhân:** `notification.properties` của Module B chưa được đổi, resource vẫn join Application cũ đã dead.

**Fix:** Đảm bảo `notification.properties` (khác với `openapi.properties`) đã được đổi:

```properties
# notification.properties — PHẢI trỏ sang CmsNotification
osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsNotification)
```

---

## Tóm tắt luồng hoạt động

```
Request: GET o/headless-api/v1.0/cms-important-notification
    │
    ▼
CXF Servlet (o/)
    │
    ▼
JAX-RS Application: CmsNotification (base=/headless-api)
    │
    ├── NotificationResourceImpl (cms-notification-impl)          → /cms-regular-notification
    └── NotificationResourceImpl (cms-important-notification-impl) → /cms-important-notification
            │
            ▼
    ImportantNotificationServiceImpl (xử lý logic)

Swagger spec (o/headless-api/v1.0/openapi.json):
    OpenAPIResourceImpl (cms-notification-impl)
        _resourceClasses = {
            NotificationResourceImpl (A),
            OpenAPIResourceImpl (A),
            ImportantNoticeV2Spec  ← spec shim cho endpoint B
        }
```