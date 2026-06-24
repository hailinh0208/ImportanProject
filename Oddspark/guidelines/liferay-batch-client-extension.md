# Hướng dẫn: Batch Client Extension trong Liferay

## Tổng quan

Batch Client Extension cho phép deploy seed data cùng với Object Definitions thông qua file `.batch-engine-data.json`. Tuy nhiên có một số giới hạn quan trọng cần biết.

---

## 1. Cấu trúc client-extension.yaml cho Batch Extension

```yaml
oddspark-batch:
  name: Oddspark Batch Data
  oAuthApplicationHeadlessServer: oddspark-batch-oahs
  type: batch
  allowedConfigurations:
    - com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition
    - com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition
    - com.liferay.object.admin.rest.dto.v1_0.ObjectFolder
    - com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship
    # KHÔNG hỗ trợ: com.liferay.object.rest.dto.v1_0.ObjectEntry (xem mục 2)
  fileNames:
    - batch/00-object-folder.batch-engine-data.json
    - batch/00-list-type-definition.batch-engine-data.json
    - batch/01-object-definition.batch-engine-data.json
    - batch/02-object-relationship.batch-engine-data.json
    # File ObjectEntry data phải import riêng (xem mục 2)
```

---

## 2. Giới hạn: ObjectEntry KHÔNG được xử lý bởi Batch Client Extension

### Vấn đề

Khi deploy batch client extension có file với `className: "com.liferay.object.rest.dto.v1_0.ObjectEntry"`, Liferay **không tạo task import** cho file đó — file bị bỏ qua hoàn toàn, không có log lỗi.

Nguyên nhân: Batch client extension framework sử dụng headless batch engine API nội bộ (`/o/headless-batch-engine/v1.0/import-task/{className}`). API này **không hỗ trợ** `ObjectEntry` với parameter `objectDefinitionExternalReferenceCode`. Gọi trực tiếp API này với ObjectEntry trả về HTTP 500.

### Cách phát hiện

- Số lượng batch tasks sau deploy bằng số files trong `fileNames` **trừ** file ObjectEntry
- Không có log lỗi — file bị skip hoàn toàn

### Giải pháp: import trực tiếp qua Custom Object batch API

Custom Object có endpoint batch riêng hoạt động đúng:

```bash
# Tạo mới (POST)
curl -X POST "http://localhost:8080/o/c/noticemasters/batch" \
  -H "Content-Type: application/json" \
  -u "test@liferay.com:test" \
  -d '[{ ... entries ... }]'

# Cập nhật (PUT)
curl -X PUT "http://localhost:8080/o/c/noticemasters/batch" \
  -H "Content-Type: application/json" \
  -u "test@liferay.com:test" \
  -d '[{ ... entries ... }]'
```

URL pattern: `o/c/{pluralObjectName}/batch` — tên plural object viết thường (ví dụ: `NoticeMaster` → `noticemasters`).

---

## 3. Format đúng cho MultiselectPicklist fields

### Vấn đề

MultiselectPicklist fields trong Liferay Objects **yêu cầu JSON array**, không phải comma-separated string.

| Format | Kết quả |
|--------|---------|
| `"competitions": "1,2,3"` | **HTTP 400** — "not mapped to a valid list type entry" |
| `"competitions": ["1","2","3"]` | **OK** |
| `"competitions": "1"` | OK (single value — cả hai format đều chấp nhận) |

### Quy tắc

```json
// SAI — comma-separated string
{ "competitions": "1,2,3" }

// ĐÚNG — JSON array
{ "competitions": ["1", "2", "3"] }

// ĐÚNG — single value cũng nên dùng array cho nhất quán
{ "competitions": ["1"] }
```

Áp dụng cho tất cả fields kiểu `MultiselectPicklist` (ví dụ: `categories`, `competitions`).

---

## 4. Thứ tự xử lý batch files

Batch files được xử lý theo thứ tự trong `fileNames`. Đặt tên file theo prefix số để đảm bảo thứ tự đúng:

```
00-object-folder.batch-engine-data.json         ← ObjectFolder trước
00-list-type-definition.batch-engine-data.json   ← ListType cùng lúc với Folder
01-object-definition.batch-engine-data.json      ← ObjectDefinition sau khi có Folder
02-object-relationship.batch-engine-data.json    ← Relationship sau khi có cả 2 Objects
```

---

## 5. Lỗi ObjectDefinitionEnableObjectEntryScheduleException

Sau khi Object Definition đã được publish, task import ObjectDefinition (UPSERT strategy) sẽ luôn fail với:

```
ObjectDefinitionEnableObjectEntryScheduleException:
Object entry schedule cannot be disabled when the object definition is published
```

Đây là lỗi **không nghiêm trọng** — Object Definition đã tồn tại và không cần update. Log cũng báo `Unable to update batch engine import task` nhưng đây là lỗi DB-level khi ghi trạng thái task.

Các tasks tiếp theo vẫn tiếp tục xử lý bình thường.

---

## 6. Checklist trước khi deploy

- [ ] `allowedConfigurations` chứa đúng classNames (không bao gồm ObjectEntry)
- [ ] Tất cả files trong `fileNames` có trong thư mục `batch/`
- [ ] MultiselectPicklist fields dùng JSON array `["value"]`
- [ ] DateTime fields có `timeStorage` trong `objectFieldSettings`
- [ ] File thứ tự đúng (folders/list trước, definitions sau)
- [ ] ObjectEntry data sẽ import riêng sau khi deploy client extension