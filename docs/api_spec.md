# API 명세

## Todo API

### 1. Todo 생성

- **엔드포인트:** `POST /api/v1/todos`
- **설명:** 새로운 Todo 항목을 생성합니다.
- **요청 본문:** `TodoCreateReq`
```json
{
  "title": "string",
  "content": "string",
  "author": "string",
  "password": "string"
}
```
- **응답:** `ApiResponse<TodoResp>`
```json
{
  "status": "CREATED",
  "message": "",
  "data": {
    "id": 1,
    "title": "string",
    "content": "string",
    "author": "string",
    "createdAt": "2025-01-01T12:00:00",
    "modifiedAt": "2025-01-01T12:00:00"
  }
}
```
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `201 Created` | Todo가 성공적으로 생성되었습니다. |
| `400 Bad Request` | 잘못된 요청 본문 (예: 필수 필드 누락, 유효성 검사 오류). |

---

### 2. Todo 목록 조회

- **엔드포인트:** `GET /api/v1/todos`
- **설명:** 조건에 따라 Todo 목록을 조회합니다.
- **요청 파라미터:** `TodoSearchCondition` (쿼리 파라미터)
  - `orderBy` (optional, string): 정렬 기준. `"필드명_정렬방향"` 형식으로 지정합니다 (예: `title_asc`, `createdAt_desc`).
      - **정렬 가능 필드:** `title`, `author`, `content`, `createdAt`, `modifiedAt`
      - **정렬 방향:** `asc` (오름차순), `desc` (내림차순)
      - **기본값:** `modifiedAt_desc`
  - `title` (optional, string): 제목으로 필터링합니다.
  - `content` (optional, string): 내용으로 필터링합니다.
  - `author` (optional, string): 작성자로 필터링합니다.
- **응답:** `ApiResponse<TodoListResp>`
```json
{
  "status": "OK",
  "message": "",
  "data": {
    "todos": [
      {
        "id": 1,
        "title": "string",
        "content": "string",
        "author": "string",
        "createdAt": "2025-01-01T12:00:00",
        "modifiedAt": "2025-01-01T12:00:00"
      }
    ]
  }
}
```
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `200 OK` | Todo 목록이 성공적으로 조회되었습니다. |

---

### 3. 특정 Todo 상세 조회 (댓글 포함)

- **엔드포인트:** `GET /api/v1/todos/{id}`
- **설명:** 특정 Todo 항목과 해당 Todo에 달린 댓글들을 조회합니다.
- **경로 변수:**
  - `id` (Long): 조회할 Todo의 ID

- **응답:** `ApiResponse<TodoWithCommentsResp>`
```json
{
  "status": "OK",
  "message": "",
  "data": {
    "todo": {
      "id": 1,
      "title": "string",
      "content": "string",
      "author": "string",
      "createdAt": "2025-01-01T12:00:00",
      "modifiedAt": "2025-01-01T12:00:00"
    },
    "comments": [
      {
        "id": 1,
        "content": "string",
        "author": "string",
        "createdAt": "2025-01-01T12:00:00",
        "modifiedAt": "2025-01-01T12:00:00"
      }
    ]
  }
}
```
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `200 OK` | Todo와 댓글이 성공적으로 조회되었습니다. |
| `404 Not Found` | 해당 ID의 Todo를 찾을 수 없습니다. |

---

### 4. Todo 수정

- **엔드포인트:** `PATCH /api/v1/todos/{id}`
- **설명:** 특정 Todo 항목을 수정합니다. `title`과 `author`는 선택적으로 수정 가능하며, `password`는 필수입니다.
- **경로 변수:**
    - `id` (Long): 수정할 Todo의 ID

- **요청 본문:** `TodoUpdateReq`
```json
{
  "title": "string (optional)",
  "author": "string (optional)",
  "password": "string (required)"
}
```
- **응답:** `ApiResponse<TodoResp>`
```json
{
  "status": "OK",
  "message": "",
  "data": {
    "id": 1,
    "title": "string",
    "content": "string",
    "author": "string",
    "createdAt": "2025-01-01T12:00:00",
    "modifiedAt": "2025-01-01T12:00:00"
  }
}
```
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `200 OK` | Todo가 성공적으로 수정되었습니다. |
| `400 Bad Request` | 잘못된 요청 본문 (예: 필수 필드 누락, 유효성 검사 오류). |
| `404 Not Found` | 해당 ID의 Todo를 찾을 수 없습니다. |
| `401 Unauthorized` | 비밀번호가 일치하지 않습니다. |

---

### 5. Todo 삭제

- **엔드포인트:** `DELETE /api/v1/todos/{id}`
- **설명:** 특정 Todo 항목을 삭제합니다.
- **경로 변수:**
  - `id` (Long): 삭제할 Todo의 ID
- **요청 헤더:**
  - `X-Todo-Password` (base64 string): Todo를 삭제하기 위한 비밀번호.

- **응답:** 없음
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `204 No Content` | Todo가 성공적으로 삭제되었습니다. |
| `400 Bad Request` | 잘못된 비밀번호 헤더 (Base64 인코딩되지 않음, 누락됨) |
| `404 Not Found` | 해당 ID의 Todo를 찾을 수 없습니다. |
| `401 Unauthorized` | 비밀번호가 일치하지 않습니다. |

---

## Comment API

### 1. 댓글 작성

- **엔드포인트:** `POST /api/v1/todos/{todoId}/comments`
- **설명:** 특정 Todo에 댓글을 작성합니다.
- **경로 변수:**
    - `todoId` (Long): 댓글을 작성할 Todo의 ID

- **요청 본문:** `CommentWriteReq`
```json
{
  "content": "string",
  "author": "string",
  "password": "string"
}
```
- **응답:** `ApiResponse<CommentResp>`
```json
{
  "status": "OK",
  "message": "",
  "data": {
    "id": 1,
    "content": "string",
    "author": "string",
    "createdAt": "2025-01-01T12:00:00",
    "modifiedAt": "2025-01-01T12:00:00"
  }
}
```
- **상태 코드:**

| 코드 | 설명 |
|---|---|
| `200 OK` | 댓글이 성공적으로 작성되었습니다. |
| `400 Bad Request` | 잘못된 요청 본문 (예: 필수 필드 누락, 유효성 검사 오류). |
| `404 Not Found` | 해당 `todoId`의 Todo를 찾을 수 없습니다. |

---