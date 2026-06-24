# Hướng dẫn: Quy tắc đặt tên DTO trong Liferay REST Builder

## Tổng quan

Liferay REST Builder sinh code Java tự động từ file `rest-openapi.yaml`. Tên field trong YAML **trực tiếp quyết định** tên getter/setter trong Java và tên key trong JSON response — không qua bất kỳ cấu hình nào khác.

---

## 1. YAML property name → Java field name

REST Builder chuyển property name trong YAML thành camelCase Java field.

| YAML property | Java field | JSON key |
|---|---|---|
| `displayPages` | `displayPages` | `"displayPages"` |
| `displayPageMulti` | `displayPageMulti` | `"displayPageMulti"` |
| `noticeTitle` | `noticeTitle` | `"noticeTitle"` |
| `importantNoticeStart` | `importantNoticeStart` | `"importantNoticeStart"` |

> Quy tắc: REST Builder **không tự động chuyển** `display_pages` (snake_case) sang `displayPages`. Dùng camelCase trực tiếp trong YAML.

---

## 2. JSON serialization KHÔNG dùng Jackson

Liferay REST Builder sinh SerDes riêng, **không dùng Jackson**. Vì vậy:

- `@JsonProperty` **không có tác dụng**
- Tên key trong JSON output được sinh từ `toString()` method trong DTO class
- `toString()` được REST Builder sinh tự động dựa trên field name trong YAML

```java
// Được sinh tự động — KHÔNG sửa bằng tay
@Override
public String toString() {
    // ...
    sb.append("\"displayPageMulti\": ");
    // ...
}
```

**Kết luận:** Muốn đổi tên JSON key → phải đổi property name trong YAML rồi chạy `buildREST`.

---

## 3. Vấn đề trùng tên field giữa các Schema (case thực tế)

### Bối cảnh

Trong `cms-important-notification`, hai schema dùng cùng tên property `displayPages`:

```yaml
# YAML cũ — dễ gây nhầm
ImportantNotification:
    properties:
        displayPages:          # array — danh sách nhiều trang
            items:
                $ref: "#/components/schemas/DisplayPage"
            type: array

Priority:
    properties:
        displayPages:          # array — nhưng chỉ map 1:1 với page
            items:
                $ref: "#/components/schemas/DisplayPage"
            type: array
```

Cả hai đều sinh `setDisplayPages()` / `getDisplayPages()`, nhưng logic nghiệp vụ khác nhau:
- `ImportantNotification.displayPages` → danh sách **nhiều trang** mà notice hiển thị
- `Priority.displayPages` → trang **duy nhất** được gán priority này

### Fix: Đặt tên khác biệt ngay từ YAML

```yaml
# YAML đã sửa — rõ ràng hơn
Item:
    properties:
        displayPageMulti:      # rõ: array nhiều trang
            items:
                $ref: "#/components/schemas/DisplayPage"
            type: array

Priority:
    properties:
        displayPageSingle:     # rõ: một trang duy nhất
            $ref: "#/components/schemas/DisplayPage"
```

Tuy nhiên, **đổi tên trong YAML vẫn không đủ** — xem vấn đề tiếp theo bên dưới.

---

## 3b. REST Builder ưu tiên tên $ref type hơn tên property trong YAML

### Vấn đề

Khi một field dùng `$ref` để trỏ tới schema khác, REST Builder **bỏ qua tên property trong YAML** và dùng **tên của schema được trỏ đến** để đặt tên Java field.

```yaml
Item:
    properties:
        displayPageMulti:          # YAML property name — bị bỏ qua!
            items:
                $ref: "#/components/schemas/DisplayPage"   # REST Builder đọc "DisplayPage"
            type: array

Priority:
    properties:
        displayPageSingle:         # YAML property name — bị bỏ qua!
            $ref: "#/components/schemas/DisplayPage"       # REST Builder đọc "DisplayPage"
```

**Kết quả sau `buildREST`:**

| YAML property | $ref type | Java field sinh ra |
|---|---|---|
| `displayPageMulti` | `DisplayPage` (array) | `displayPages` ← không phải `displayPageMulti` |
| `displayPageSingle` | `DisplayPage` (single) | `displayPages` ← không phải `displayPageSingle` |

REST Builder lấy tên từ `DisplayPage` → chuyển thành `displayPage`, sau đó thêm `s` → `displayPages`, bất kể tên property trong YAML là gì.

### Hậu quả

Cả hai field trong hai DTO khác nhau đều bị sinh ra với **cùng tên `displayPages`**, dù có ý nghĩa nghiệp vụ khác nhau. Logic code dùng tên này sẽ không phân biệt được.

### Fix bắt buộc: sửa thủ công sau mỗi lần `buildREST`

Vì REST Builder luôn ghi đè tên field theo `$ref`, cần **sửa tay** trong các file generated sau khi chạy `buildREST`. Các file bị ảnh hưởng (thường là 6 file):

```
cms-important-notification-api/
  └── dto/v1_0/Item.java              ← đổi displayPages → displayPageMulti
  └── dto/v1_0/Priority.java          ← đổi displayPages → displayPageSingle

cms-important-notification-impl/
  └── dto/v1_0/ItemSerDes.java        ← đổi displayPages → displayPageMulti
  └── dto/v1_0/PrioritySerDes.java    ← đổi displayPages → displayPageSingle

cms-important-notification-client/
  └── dto/v1_0/Item.java              ← đổi displayPages → displayPageMulti
  └── dto/v1_0/Priority.java          ← đổi displayPages → displayPageSingle
```

Trong mỗi file, tìm và thay thế tất cả occurrences:
- `getDisplayPages` / `setDisplayPages` / `"displayPages"` → tên đúng tương ứng

> **Lưu ý:** Các file này có `LIFERAY-REST-BUILDER-HASH` — mỗi khi YAML thay đổi và `buildREST` được chạy lại, cần lặp lại bước sửa thủ công này.

### Cách tránh vấn đề lâu dài

Dùng schema **inline** thay vì `$ref` cho các field cần kiểm soát tên:

```yaml
Item:
    properties:
        displayPageMulti:
            items:
                properties:          # inline — REST Builder dùng property name
                    key:
                        type: integer
                    name:
                        type: string
                type: object
            type: array
```

Khi field dùng inline schema (không có `$ref`), REST Builder dùng đúng tên property trong YAML. Nhược điểm: không tái sử dụng được schema `DisplayPage` ở nơi khác.

---

## 4. Cơ chế LIFERAY-REST-BUILDER-HASH

Mỗi file được sinh bởi REST Builder có dòng comment ở cuối:

```java
// LIFERAY-REST-BUILDER-HASH:337890469
```

Cơ chế hoạt động:
- Hash được tính từ **nội dung YAML**
- Khi chạy `buildREST`:
  - Nếu hash trong file **khớp** với hash tính từ YAML hiện tại → **bỏ qua**, không sinh lại
  - Nếu hash **không khớp** (YAML đã thay đổi) → **sinh lại và ghi đè** file

### Files bị ghi đè khi chạy lại `buildREST`

| File | Bị ghi đè? | Lưu ý |
|---|---|---|
| `BaseXxxResourceImpl.java` | **Có** | Chứa `@Path`, method signatures |
| `XxxResource.java` (interface) | **Có** | |
| `XxxApplication.java` | **Có** | Mất `service = Application.class` nếu đã xóa |
| DTO classes (e.g. `Item.java`) | **Có** | Mất đổi tên thủ công |
| SerDes classes | **Có** | |
| `XxxResourceImpl.java` | **Không** | File implementation, do developer viết |
| `notification.properties` | **Không** | Nằm trong `resources/` |
| `openapi.properties` | **Không** | Nằm trong `resources/` |

> **Quan trọng:** Các file có `@Generated` và `LIFERAY-REST-BUILDER-HASH` đều bị ghi đè. Không sửa tay các file này — hãy đổi trong YAML.

---

## 5. Quy trình đổi tên field an toàn

Nếu cần đổi tên một field trong DTO:

1. **Đổi property name trong `rest-openapi.yaml`**
2. **Chạy `buildREST`** để sinh lại code
3. **Cập nhật `XxxResourceImpl.java`** — dùng getter/setter mới
4. **Cập nhật các service class** liên quan (builder, filter, v.v.)
5. **Build và deploy**

```bash
blade gw :modules:<module>:<module>-impl:buildREST
blade gw :modules:<module>:<module>-impl:deploy
```

---

## 6. Best practices khi đặt tên property trong YAML

- Dùng **camelCase** trong YAML, REST Builder không xử lý snake_case
- Đặt tên **mô tả rõ ngữ nghĩa** — tránh dùng tên chung chung như `data`, `list`, `value`
- Khi hai schema có field cùng kiểu nhưng ngữ nghĩa khác nhau → **đặt tên khác nhau** ngay từ đầu (ví dụ: `displayPageMulti` vs `displayPageSingle`)
- Tránh tên trùng với Java reserved words hoặc Liferay framework field (`id`, `status` đôi khi có hành vi đặc biệt)
- Array fields nên có suffix gợi ý plurality: `items`, `entries`, `Multi`, hoặc dùng số nhiều rõ ràng