# Study App - Ứng Dụng Học Tập

## Mô tả
Study App là một ứng dụng học tập Android hỗ trợ học sinh trung học cơ sở (lớp 6-9) với các tính năng đọc sách giáo khoa, thảo luận, làm quiz và chatbot hỗ trợ học tập.

## Tính năng chính

### 🔐 Xác thực người dùng
- **Đăng nhập thông thường**: Username/Password
- **Đăng nhập Google**: Tích hợp Google Sign-In với Firebase Authentication
- **Đăng ký tài khoản mới**
- **Chỉnh sửa thông tin cá nhân**

### 📚 Thư viện sách giáo khoa
- **Hỗ trợ 4 khối lớp**: Lớp 6, 7, 8, 9
- **3 bộ sách giáo khoa**:
  - Cánh diều
  - Chân trời sáng tạo  
  - Kết nối tri thức
- **Đọc PDF trực tuyến**: Xem sách với PDF viewer tích hợp

### 💬 Hệ thống thảo luận
- Đặt câu hỏi và thảo luận với cộng đồng
- Tìm kiếm câu hỏi
- Tương tác với các bài đăng

### 🎯 Hệ thống Quiz
- Làm bài kiểm tra trực tuyến
- Đánh giá kết quả học tập

### 🤖 Chatbot hỗ trợ
- Hỗ trợ học tập thông minh
- Trả lời câu hỏi tự động

### 🏫 Phòng học ảo
- Môi trường học tập trực tuyến
- Quản lý lớp học

## Kiến trúc hệ thống

### Frontend (Android App)
- **Ngôn ngữ**: Java
- **Architecture**: Fragment-based với Bottom Navigation
- **UI Framework**: Material Design Components
- **PDF Viewer**: android-pdf-viewer library

### Backend (Node.js Server)
- **Framework**: Express.js
- **Database**: MySQL
- **Authentication**: 
  - JWT cho đăng nhập thông thường
  - Google OAuth2 cho đăng nhập Google
- **API**: RESTful APIs

## Cấu trúc thư mục

```
study_app_fragment/
├── app/                           # Android App
│   ├── src/main/java/com/example/study_app/
│   │   ├── fragments/            # Các Fragment chính
│   │   │   ├── HomeFragment.java        # Trang chủ - chọn sách
│   │   │   ├── ClassroomFragment.java   # Phòng học
│   │   │   ├── DiscussionFragment.java  # Thảo luận
│   │   │   ├── QuizFragment.java        # Quiz
│   │   │   └── ChatFragment.java        # Chatbot
│   │   ├── model/                # Data models
│   │   ├── adapter/              # RecyclerView adapters
│   │   ├── data/                 # API clients
│   │   ├── LoginActivity.java    # Màn hình đăng nhập
│   │   ├── RegisterActivity.java # Màn hình đăng ký
│   │   ├── MainActivityNew.java  # Activity chính
│   │   └── ReaderActivity.java   # Đọc PDF
│   └── src/main/res/            # Resources (layouts, strings, etc.)
├── backend/                      # Node.js Backend
│   ├── server.js                # Server chính
│   ├── package.json             # Dependencies
│   └── database_setup.sql       # Database schema
└── build.gradle.kts             # Android build config
```

## Cài đặt và chạy

### Yêu cầu hệ thống
- **Android Studio** Arctic Fox hoặc mới hơn
- **Android SDK** API level 24+ (Android 7.0)
- **Node.js** v14+ và npm
- **MySQL** 8.0+
- **Tài khoản Firebase** (cho Google Sign-In)

### Cài đặt Backend

1. **Cài đặt dependencies**:
```bash
cd backend
npm install
```

2. **Cấu hình database**:
   - Tạo database MySQL
   - Import file `database_setup.sql`

3. **Cấu hình môi trường**:
   Tạo file `.env`:
```env
DB_HOST=localhost
DB_USER=your_username
DB_PASSWORD=your_password
DB_NAME=study_app
DB_PORT=3306
PORT=3000
```

4. **Chạy server**:
```bash
npm start
# Hoặc development mode:
npm run dev
```

### Cài đặt Android App

1. **Mở project trong Android Studio**

2. **Cấu hình Firebase**:
   - Tạo project Firebase mới
   - Enable Authentication và Google Sign-In
   - Thêm SHA-1 fingerprint: `83:D1:F5:65:30:A3:A0:A9:C6:F1:D4:3A:2E:76:88:52:07:45:1E:48`
   - Download `google-services.json` và đặt vào thư mục `app/`

3. **Cập nhật API endpoint**:
   - Trong `ApiClient.java`, cập nhật base URL của server backend

4. **Build và chạy**:
```bash
./gradlew assembleDebug
# Hoặc chạy trực tiếp từ Android Studio
```

## Dependencies chính

### Android
- **Material Components**: UI components
- **Fragment & Navigation**: Navigation architecture
- **Retrofit**: HTTP client
- **Google Play Services**: Google Sign-In
- **PDF Viewer**: Hiển thị PDF
- **RecyclerView**: Danh sách và cards

### Backend
- **Express.js**: Web framework
- **MySQL2**: Database driver
- **Google Auth Library**: Xác thực Google OAuth2
- **CORS**: Cross-origin requests
- **Body Parser**: Parse request body

## API Endpoints

### Authentication
- `POST /api/login` - Đăng nhập thông thường
- `POST /api/register` - Đăng ký tài khoản
- `POST /api/google-login` - Đăng nhập Google

### User Management
- `GET /api/user/:id` - Lấy thông tin user
- `PUT /api/user/:id` - Cập nhật thông tin user

### Discussion
- `GET /api/discussions` - Lấy danh sách thảo luận
- `POST /api/discussions` - Tạo thảo luận mới

## Cấu hình Firebase

1. **Tạo Firebase Project**:
   - Vào [Firebase Console](https://console.firebase.google.com)
   - Tạo project mới với tên `studyapp-da824`

2. **Enable Authentication**:
   - Authentication > Sign-in method
   - Enable "Google" provider

3. **Thêm Android App**:
   - Package name: `com.example.study_app`
   - SHA-1: `83:D1:F5:65:30:A3:A0:A9:C6:F1:D4:3A:2E:76:88:52:07:45:1E:48`

4. **Download config**:
   - Download `google-services.json`
   - Đặt vào `app/` directory

## Troubleshooting

### Lỗi Google Sign-In

**Error 10**: Firebase chưa cấu hình đúng
- Kiểm tra SHA-1 fingerprint đã được thêm
- Đảm bảo Authentication đã được enable
- Download lại `google-services.json` mới nhất

**Error 400**: Server backend issues
- Kiểm tra server đang chạy
- Verify API endpoints
- Check network connectivity

### Lỗi Database
- Đảm bảo MySQL server đang chạy
- Kiểm tra credentials trong `.env`
- Import đầy đủ database schema

## Đóng góp

1. Fork project
2. Tạo feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Tạo Pull Request

## License

Dự án này được phát hành dưới giấy phép MIT. Xem file `LICENSE` để biết thêm chi tiết.

## Tác giả

- **Developer**: Study App Team
- **Contact**: [Email/GitHub]

## Changelog

### v1.0.0
- ✅ Đăng nhập/đăng ký cơ bản
- ✅ Google Sign-In integration
- ✅ PDF reader cho sách giáo khoa
- ✅ Hệ thống thảo luận
- ✅ Quiz system
- ✅ Chatbot cơ bản
- ✅ Bottom navigation với 5 tabs chính

---

**Lưu ý**: Đây là phiên bản đầu tiên của ứng dụng. Các tính năng có thể được cập nhật và cải thiện trong các phiên bản tương lai.
