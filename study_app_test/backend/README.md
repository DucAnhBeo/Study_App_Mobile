# Study App Backend

Backend API cho ứng dụng Study App với chức năng đăng nhập và đăng ký sử dụng MySQL.

## Cài đặt

### 1. Cài đặt Node.js và MySQL
- Tải và cài đặt Node.js từ [nodejs.org](https://nodejs.org/)
- Cài đặt MySQL từ [mysql.com](https://www.mysql.com/)

### 2. Cài đặt dependencies
```bash
cd backend
npm install
```

### 3. Cấu hình Database
1. Tạo database MySQL:
```sql
CREATE DATABASE study_app_db;
```

2. Cập nhật file `.env` với thông tin database của bạn:
```
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=your_mysql_password
DB_NAME=study_app_db
DB_PORT=3306
```

### 4. Chạy server
```bash
# Chạy bình thường
npm start

# Hoặc chạy với nodemon (tự động restart khi có thay đổi)
npm run dev
```

Server sẽ chạy tại: http://localhost:3000

## API Endpoints

### 1. Test Connection
- **URL**: `GET /`
- **Mô tả**: Kiểm tra server có hoạt động không

### 2. Đăng ký
- **URL**: `POST /api/register`
- **Body**:
```json
{
  "username": "your_username",
  "password": "your_password"
}
```
- **Response thành công**:
```json
{
  "success": true,
  "message": "Đăng ký thành công! Vui lòng đăng nhập."
}
```

### 3. Đăng nhập
- **URL**: `POST /api/login`
- **Body**:
```json
{
  "username": "your_username",
  "password": "your_password"
}
```
- **Response thành công**:
```json
{
  "success": true,
  "message": "Đăng nhập thành công",
  "user": {
    "id": 1,
    "username": "your_username"
  }
}
```

### 4. Xem danh sách users (để test)
- **URL**: `GET /api/users`
- **Response**:
```json
{
  "success": true,
  "users": [
    {
      "id": 1,
      "username": "user1",
      "created_at": "2025-09-14T..."
    }
  ]
}
```

## Tính năng

### Bảo mật
- Mật khẩu được mã hóa bằng bcrypt
- Validation đầu vào
- Xử lý lỗi chi tiết

### Validation
- Tên đăng nhập: tối thiểu 3 ký tự, duy nhất
- Mật khẩu: tối thiểu 6 ký tự

### Database
- Tự động tạo bảng `users` khi khởi động
- Lưu trữ an toàn với mật khẩu đã mã hóa

## Cấu trúc Database

### Bảng users
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Tích hợp với Android App

Trong Android app, bạn có thể sử dụng Retrofit hoặc Volley để gọi API:

### Ví dụ với Retrofit:
```java
// API Interface
public interface AuthAPI {
    @POST("api/register")
    Call<ResponseBody> register(@Body LoginRequest request);
    
    @POST("api/login")
    Call<ResponseBody> login(@Body LoginRequest request);
}

// Request Model
public class LoginRequest {
    private String username;
    private String password;
    // constructors, getters, setters
}
```

## Lưu ý
- Đảm bảo MySQL server đang chạy trước khi start backend
- Thay đổi thông tin database trong file `.env` cho phù hợp
- Trong production, sử dụng HTTPS và cấu hình bảo mật tốt hơn
