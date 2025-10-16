# Study App - Ứng dụng học tập cho học sinh THCS

## 1. Giới thiệu Dự án (Introduction)
Tên Dự án: Study App

Mô tả ngắn: Ứng dụng Android hỗ trợ học sinh trung học cơ sở (lớp 6-9) học tập, đọc sách giáo khoa, làm quiz, thảo luận và sử dụng chatbot hỗ trợ học tập.

## 2. Tính năng Chính (Key Features)
- Đăng nhập/Đăng ký người dùng (tài khoản thường và Google)
- Quản lý thông tin cá nhân
- Đọc sách giáo khoa PDF trực tuyến (4 khối lớp, 3 bộ sách)
- Hệ thống thảo luận: đặt câu hỏi, trả lời, tìm kiếm
- Làm bài kiểm tra (Quiz)
- Chatbot hỗ trợ học tập
- Phòng học ảo, quản lý lớp học

## 3. Công nghệ & Nền tảng (Technology Stack & Platform)
- Nền tảng: Android
- Ngôn ngữ lập trình: Java
- Framework/Thư viện chính: Android SDK, Material Design, android-pdf-viewer, Retrofit, Firebase, Google Play Services
- Backend: Node.js (Express.js), MySQL, Google Auth Library

## 4. Cài đặt & Chạy Dự án (Setup & Installation)
### 4.1. Điều kiện Tiên quyết (Prerequisites)
- Android Studio Arctic Fox hoặc mới hơn
- Android SDK API level 24+
- Node.js v14+ và npm
- MySQL 8.0+
- Tài khoản Firebase

### 4.2. Các bước Cài đặt
#### Clone Repository:
```bash
git clone [địa chỉ repo]
cd study_app_fragment
```
#### Cài đặt Dependencies:
```bash
# Backend
cd backend
npm install
# Android app: Mở bằng Android Studio, Gradle sẽ tự động cài dependencies
```
#### Cấu hình Môi trường:
- Backend: Tạo file `.env` trong thư mục backend theo mẫu:
```
DB_HOST=localhost
DB_USER=your_username
DB_PASSWORD=your_password
DB_NAME=study_app
DB_PORT=3306
PORT=3000
```
- Android: Tạo project Firebase, thêm SHA-1, enable Google Sign-In, tải `google-services.json` về đặt vào thư mục `app/`


## 5. Sử dụng (Usage)
### 5.1. Hướng dẫn cho Người dùng 
#### Đăng nhập và Đăng ký:
- Mở ứng dụng Study App
- Chọn "Đăng ký" để tạo tài khoản mới hoặc "Đăng nhập" nếu đã có tài khoản
- Có thể đăng nhập bằng Google hoặc tài khoản thường

#### Đọc sách giáo khoa:
- Vào tab "Sách giáo khoa"
- Chọn khối lớp (6, 7, 8, 9)
- Chọn bộ sách (Cánh Diều, Chân Trời Sáng Tạo, Kết Nối Tri Thức)
- Chọn môn học và cuốn sách muốn đọc

#### Hệ thống thảo luận:
- Vào tab "Thảo luận"
- Đặt câu hỏi: Nhấn nút "Đặt câu hỏi", nhập tiêu đề và nội dung
- Trả lời câu hỏi: Chọn câu hỏi, nhập câu trả lời
- Tìm kiếm: Sử dụng thanh tìm kiếm để tìm câu hỏi liên quan

#### Làm Quiz:
- Vào tab "Quiz"
- Chọn môn học và cấp độ
- Làm bài theo thời gian quy định
- Xem kết quả và đáp án sau khi hoàn thành

#### Chatbot hỗ trợ:
- Vào tab "Chatbot"
- Đặt câu hỏi về kiến thức học tập
- Nhận câu trả lời và gợi ý học tập


## 6. Cấu trúc Thư mục Chính (Directory Structure)
```
study_app_fragment/
├── app/                           # Android App
│   ├── src/main/java/com/example/study_app/
│   │   ├── fragments/            # Các Fragment chính
│   │   ├── model/                # Data models
│   │   ├── adapter/              # RecyclerView adapters
│   │   ├── data/                 # API clients
│   │   ├── LoginActivity.java    # Màn hình đăng nhập
│   │   ├── RegisterActivity.java # Màn hình đăng ký
│   │   ├── MainActivityNew.java  # Activity chính
│   │   └── ReaderActivity.java   # Đọc PDF
│   └── src/main/res/             # Resources (layouts, strings, etc.)
├── backend/                      # Node.js Backend
│   ├── server.js                # Server chính
│   ├── package.json             # Dependencies
│   └── database_setup.sql       # Database schema
└── build.gradle.kts             # Android build config
```


