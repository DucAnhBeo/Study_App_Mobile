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

-- Xóa dữ liệu cũ và thêm dữ liệu mẫu mới
DELETE FROM users WHERE username IN ('admin', 'testuser');

-- Thêm tài khoản admin với mật khẩu 123456 (đã hash)
INSERT INTO users (username, password) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'),
('testuser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi');

-- Hiển thị cấu trúc bảng và dữ liệu
DESCRIBE users;
SELECT username, created_at FROM users;
