-- Tạo database cho Study App
CREATE DATABASE IF NOT EXISTS study_app_db;
USE study_app_db;

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo index cho username để tăng tốc độ tìm kiếm
CREATE INDEX idx_username ON users(username);

-- Thêm tài khoản admin với mật khẩu 123456
('admin', '123456'),
('testuser', '123456');

-- Hiển thị cấu trúc bảng và dữ liệu
DESCRIBE users;
SELECT username, created_at FROM users;
