package com.example.study_app.model;

/**
 * Quiz Model Class - Lớp mô hình dữ liệu Quiz
 *
 * TRẠNG THÁI: ĐANG PHÁT TRIỂN - CHƯA HOÀN THÀNH
 *
 * Mục đích: Đại diện cho một bài kiểm tra trắc nghiệm trong hệ thống
 *
 * Các thuộc tính dự kiến trong tương lai:
 * - int id: ID duy nhất của quiz
 * - String title: Tiêu đề quiz (VD: "Toán học lớp 6 - Chương 1")
 * - String description: Mô tả chi tiết về quiz
 * - String subject: Môn học (Toán, Văn, Anh, Lý, Hóa, Sinh, Sử, Địa)
 * - String grade: Lớp học (Lớp 6, Lớp 7, Lớp 8, Lớp 9)
 * - String textbook: Sách giáo khoa (Cánh diều, Chân trời sáng tạo, Kết nối tri thức)
 * - int questionCount: Số lượng câu hỏi
 * - int timeLimit: Thời gian làm bài (phút)
 * - String difficulty: Độ khó (Dễ, Trung bình, Khó)
 * - boolean isActive: Trạng thái hoạt động
 * - Date createdDate: Ngày tạo
 * - Date updatedDate: Ngày cập nhật
 * - List<Question> questions: Danh sách câu hỏi
 *
 * Các phương thức dự kiến:
 * - Getter/Setter cho tất cả thuộc tính
 * - calculateScore(): Tính điểm dựa trên câu trả lời
 * - getRandomQuestions(int count): Lấy ngẫu nhiên số câu hỏi
 * - isTimeUp(): Kiểm tra hết thời gian
 * - getPassingScore(): Lấy điểm đạt (thường 50% hoặc 60%)
 *
 * Tích hợp Database:
 * - Kết nối với bảng 'quizzes' trong MySQL
 * - Quan hệ với bảng 'questions' (1-nhiều)
 * - Quan hệ với bảng 'quiz_results' để lưu kết quả
 *
 * Hiện tại: Class placeholder để tránh lỗi compile
 */
public class Quiz {
    // TODO: Phát triển chức năng Quiz trong tương lai
    // Chức năng này sẽ bao gồm:
    // - Quản lý câu hỏi trắc nghiệm theo môn học và lớp
    // - Hệ thống chấm điểm tự động
    // - Theo dõi kết quả và thống kê
    // - Phân loại theo độ khó và chủ đề
    // - Tích hợp với backend MySQL để lưu trữ

    /* ========== CÁC THUỘC TÍNH TƯƠNG LAI ========== */
    /*
    private int id;                    // ID duy nhất
    private String title;              // Tiêu đề quiz
    private String description;        // Mô tả
    private String subject;            // Môn học
    private String grade;              // Lớp
    private String textbook;           // Sách giáo khoa
    private int questionCount;         // Số câu hỏi
    private int timeLimit;             // Thời gian (phút)
    private String difficulty;         // Độ khó
    private boolean isActive;          // Trạng thái
    private Date createdDate;          // Ngày tạo
    private Date updatedDate;          // Ngày cập nhật
    private List<Question> questions;  // Danh sách câu hỏi
    */

    /* ========== CÁC PHƯƠNG THỨC TƯƠNG LAI ========== */
    /*
    // Constructor
    public Quiz(String title, String subject, String grade, String textbook) {
        this.title = title;
        this.subject = subject;
        this.grade = grade;
        this.textbook = textbook;
        this.isActive = true;
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.questions = new ArrayList<>();
    }

    // Getter methods
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSubject() { return subject; }
    public String getGrade() { return grade; }
    public String getTextbook() { return textbook; }
    public int getQuestionCount() { return questionCount; }
    public int getTimeLimit() { return timeLimit; }
    public String getDifficulty() { return difficulty; }
    public boolean isActive() { return isActive; }

    // Setter methods
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    // Business logic methods
    public int calculateScore(List<Answer> answers) {
        // Logic tính điểm dựa trên câu trả lời
        return 0;
    }

    public List<Question> getRandomQuestions(int count) {
        // Lấy ngẫu nhiên số câu hỏi từ danh sách
        return new ArrayList<>();
    }

    public boolean isTimeUp(long startTime) {
        // Kiểm tra hết thời gian làm bài
        return false;
    }

    public int getPassingScore() {
        // Điểm đạt (thường 60% tổng điểm)
        return (int)(questionCount * 0.6);
    }
    */
}
