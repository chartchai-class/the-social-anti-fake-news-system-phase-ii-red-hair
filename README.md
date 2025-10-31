# Red Hair - Anti Fake News System

---
The Project for SE331 Component-Based Software Development 
where the users can post news, comments and vote the news as fake or not fake.

This repository is meant for developing **Backend** for the project.

Course code: 953331

Semester: 1/2025


## Team members:

- 652115050 - Siraphop Guntiya (Sec - 701)
- 662115510 - Min Thant Ko (Sec - 701)
- 662115515 - Saw Rory Yin (Sec - 701)

---

## Deployed link

- Frontend: http://54.167.67.248:8001
- Backend (phpMyAdmin) : http://98.93.147.121:9000

*Deployed using AWS EC2.*

## Repository Links
- Frontend Repository: https://github.com/chartchai-class/project-01-the-anti-fake-news-system-red-hair
- Backend Repository: https://github.com/chartchai-class/the-social-anti-fake-news-system-phase-ii-red-hair
---

## Api Endpoints


### Authentication

| Method | Endpoint | Description | Parameters |
| :--- | :--- | :--- | :--- |
| POST | `/api/v1/auth/register` | For account registering. | `RequestBody: RegisterRequest` |
| POST | `/api/v1/auth/authenticate` | For authentication in login. | `RequestBody: AuthenticationRequest` |
| POST | `/api/v1/auth/refresh-token` | For refreshing token. | - |

---

### News

| Method | Endpoint | Description | Parameters |
| :--- | :--- | :--- | :--- |
| GET | `/news` | For fetching all visible news. | `ReqParam: "status", "searchBy", "search", "page", "size"` |
| GET | `/admin/news` | For fetching all news including deleted ones. Need Authentication as Admin. | `ReqParam: "status", "searchBy", "search", "page", "size"` |
| GET | `/news/{id}` | For fetching specific news by news ID. | `PathVariable: "id"` |
| POST | `/news` | For posting news. | `RequestBody: News` |
| DELETE | `/news/{id}` | For deleting specific news by ID permanently. | `PathVariable: "id"` |
| POST | `/news/{id}/toggle-delete` | For soft deleting and undo-deleting news. | `PathVariable: "id"`, `RequestBody: News` |

---

### Comments

| Method | Endpoint | Description | Parameters |
| :--- | :--- | :--- | :--- |
| GET | `/news/{id}/comments` | For getting only visible comments for users. | `PathVariable: "id" (newsId)`, `RequestParam: "size", "page"` |
| GET | `/admin/news/{id}/comments` | For getting all news including deleted ones by admin. | `PathVariable: "id" (newsId)`, `RequestParam: "size", "page"` |
| POST | `/news/{newsId}/comments` | For posting comment in specific news. | `PathVariable: "newsId"`, `RequestParam: "authorId"`, `RequestBody: Comment` |
| DELETE | `/comments/{id}` | For deleting comment permanently. | `PathVariable: "id"` |
| POST | `/comments/{id}/toggle-delete` | For soft deleting comments. Only admin can see deleted comments. | `PathVariable: "id"`, `RequestBody: Comment` |

---

### User Profile

| Method | Endpoint | Description | Parameters |
| :--- | :--- | :--- | :--- |
| GET | `/users/profiles` | For getting all user profiles by admin. | `RequestParam: "_limit", "_page"` |
| GET | `/users/profiles/{id}` | For getting specific user profile. (Separated for security). | `PathVariable: "id"` |
| PUT | `/users/profiles/{id}` | For Updating roles (performed by admin). | `PathVariable: "id"`, `RequestBody: List<Role>` |
| GET | `/profiles/me` | For getting own profile. Validated by auth header. | - |
| PUT | `/profiles/me` | For edit profile details. | `RequestBody: UserProfile` |

---

### File Upload

| Method | Endpoint | Description | Parameters |
| :--- | :--- | :--- | :--- |
| POST | `/uploadImage` | For uploading image file ( profile, news, comment). | `RequestParam: "image" ` |
