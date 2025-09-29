package com.example.study_app.model;

/**
 * Lesson Model Class - Lớp mô hình dữ liệu Lesson (Bài học)
 *
 * TRẠNG THÁI: ĐANG PHÁT TRIỂN - CHƯA HOÀN THÀNH
 *
 * Mục đích: Đại diện cho một bài học trong hệ thống học tập
 *
 * Các thuộc tính dự kiến trong tương lai:
 * - int id: ID duy nhất của bài học
 * - String title: Tiêu đề bài học (VD: "Bài 1: Tập hợp số tự nhiên")
 * - String description: Mô tả ngắn gọn về nội dung bài học
 * - String content: Nội dung chi tiết bài học (text/HTML)
 * - String subject: Môn học (Toán, Văn, Anh, Lý, Hóa, Sinh, Sử, Địa)
 * - String grade: Lớp học (Lớp 6, Lớp 7, Lớp 8, Lớp 9)
 * - String textbook: Sách giáo khoa (Cánh diều, Chân trời sáng tạo, Kết nối tri thức)
 * - String chapter: Chương (VD: "Chương 1", "Chương 2")
 * - int lessonNumber: Số thứ tự bài học trong chương
 * - int duration: Thời lượng học dự kiến (phút)
 * - String videoUrl: Link video bài giảng (YouTube, Vimeo, etc.)
 * - String audioUrl: Link file audio bài giảng
 * - List<String> imageUrls: Danh sách hình ảnh minh họa
 * - List<String> documentUrls: Tài liệu đính kèm (PDF, DOC, etc.)
 * - String difficulty: Độ khó (Cơ bản, Nâng cao, Chuyên sâu)
 * - boolean isPublished: Trạng thái xuất bản
 * - Date createdDate: Ngày tạo
 * - Date updatedDate: Ngày cập nhật
 * - String teacherNotes: Ghi chú dành cho giáo viên
 * - List<String> learningObjectives: Mục tiêu học tập
 * - List<String> prerequisites: Kiến thức cần có trước khi học
 *
 * Các phương thức dự kiến:
 * - Getter/Setter cho tất cả thuộc tính
 * - getEstimatedReadingTime(): Tính thời gian đọc dự kiến
 * - hasVideo(): Kiểm tra có video không
 * - hasAudio(): Kiểm tra có audio không
 * - getFormattedContent(): Lấy nội dung đã format
 * - isCompleted(userId): Kiểm tra user đã hoàn thành bài học chưa
 * - getProgress(userId): Lấy tiến độ học của user
 * - addToFavorites(userId): Thêm vào danh sách yêu thích
 * - generateSummary(): Tạo tóm tắt bài học
 *
 * Tích hợp Database:
 * - Kết nối với bảng 'lessons' trong MySQL
 * - Quan hệ với bảng 'lesson_progress' để theo dõi tiến độ
 * - Quan hệ với bảng 'lesson_favorites' để lưu yêu thích
 * - Quan hệ với bảng 'lesson_comments' cho phần bình luận
 *
 * Tích hợp Multimedia:
 * - Hỗ trợ embed video từ YouTube, Vimeo
 * - Audio player tích hợp
 * - Image gallery với zoom
 * - PDF viewer tích hợp
 *
 * Hiện tại: Class placeholder để tránh lỗi compile
 */
public class Lesson {
    // TODO: Phát triển chức năng Lesson trong tương lai
    // Chức năng này sẽ bao gồm:
    // - Quản lý nội dung bài học theo môn học và lớp
    // - Video bài giảng với player tích hợp
    // - Tài liệu đính kèm (PDF, images, audio)
    // - Theo dõi tiến độ học tập của từng học sinh
    // - Hệ thống đánh dấu yêu thích và ghi chú
    // - Tích hợp với backend MySQL để lưu trữ nội dung
    // - Hỗ trợ offline reading
    // - Search và filter bài học

    /* ========== CÁC THUỘC TÍNH TƯƠNG LAI ========== */
    /*
    // Basic information
    private int id;                           // ID duy nhất
    private String title;                     // Tiêu đề bài học
    private String description;               // Mô tả ngắn
    private String content;                   // Nội dung chi tiết

    // Classification
    private String subject;                   // Môn học
    private String grade;                     // Lớp
    private String textbook;                  // Sách giáo khoa
    private String chapter;                   // Chương
    private int lessonNumber;                 // Số thứ tự bài

    // Media and resources
    private String videoUrl;                  // Link video
    private String audioUrl;                  // Link audio
    private List<String> imageUrls;           // Danh sách hình ảnh
    private List<String> documentUrls;        // Tài liệu đính kèm

    // Metadata
    private int duration;                     // Thời lượng (phút)
    private String difficulty;                // Độ khó
    private boolean isPublished;              // Trạng thái xuất bản
    private Date createdDate;                 // Ngày tạo
    private Date updatedDate;                 // Ngày cập nhật

    // Educational content
    private String teacherNotes;              // Ghi chú giáo viên
    private List<String> learningObjectives;  // Mục tiêu học tập
    private List<String> prerequisites;       // Kiến thức tiên quyết

    // User interaction
    private int viewCount;                    // Số lượt xem
    private double averageRating;             // Đánh giá trung bình
    private int favoriteCount;                // Số lượt yêu thích
    */

    /* ========== CÁC PHƯƠNG THỨC TƯƠNG LAI ========== */
    /*
    // Constructor
    public Lesson(String title, String subject, String grade, String textbook) {
        this.title = title;
        this.subject = subject;
        this.grade = grade;
        this.textbook = textbook;
        this.isPublished = false;
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.imageUrls = new ArrayList<>();
        this.documentUrls = new ArrayList<>();
        this.learningObjectives = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.viewCount = 0;
        this.averageRating = 0.0;
        this.favoriteCount = 0;
    }

    // Getter methods - Basic info
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }
    public String getSubject() { return subject; }
    public String getGrade() { return grade; }
    public String getTextbook() { return textbook; }
    public String getChapter() { return chapter; }
    public int getLessonNumber() { return lessonNumber; }

    // Getter methods - Media
    public String getVideoUrl() { return videoUrl; }
    public String getAudioUrl() { return audioUrl; }
    public List<String> getImageUrls() { return imageUrls; }
    public List<String> getDocumentUrls() { return documentUrls; }

    // Getter methods - Metadata
    public int getDuration() { return duration; }
    public String getDifficulty() { return difficulty; }
    public boolean isPublished() { return isPublished; }
    public Date getCreatedDate() { return createdDate; }
    public Date getUpdatedDate() { return updatedDate; }

    // Setter methods
    public void setTitle(String title) {
        this.title = title;
        this.updatedDate = new Date();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedDate = new Date();
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedDate = new Date();
    }

    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setPublished(boolean published) { this.isPublished = published; }

    // Business logic methods

    /**
     * Tính thời gian đọc dự kiến (khoảng 200 từ/phút)
     */
    /*
    public int getEstimatedReadingTime() {
        if (content == null || content.isEmpty()) {
            return 0;
        }

        String[] words = content.split("\\s+");
        int wordCount = words.length;
        return Math.max(1, wordCount / 200); // Tối thiểu 1 phút
    }

    /**
     * Kiểm tra có video hay không
     */
    /*
    public boolean hasVideo() {
        return videoUrl != null && !videoUrl.trim().isEmpty();
    }

    /**
     * Kiểm tra có audio hay không
     */
    /*
    public boolean hasAudio() {
        return audioUrl != null && !audioUrl.trim().isEmpty();
    }

    /**
     * Kiểm tra có tài liệu đính kèm hay không
     */
    /*
    public boolean hasDocuments() {
        return documentUrls != null && !documentUrls.isEmpty();
    }

    /**
     * Lấy nội dung đã được format HTML
     */
    /*
    public String getFormattedContent() {
        if (content == null) {
            return "";
        }

        // Convert markdown to HTML or apply formatting
        return content.replace("\n", "<br>");
    }

    /**
     * Tạo tóm tắt bài học (100 từ đầu)
     */
    /*
    public String generateSummary() {
        if (content == null || content.isEmpty()) {
            return description != null ? description : "";
        }

        String[] words = content.split("\\s+");
        if (words.length <= 100) {
            return content;
        }

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            summary.append(words[i]).append(" ");
        }

        return summary.toString().trim() + "...";
    }

    /**
     * Thêm hình ảnh vào bài học
     */
    /*
    public void addImage(String imageUrl) {
        if (imageUrls == null) {
            imageUrls = new ArrayList<>();
        }
        imageUrls.add(imageUrl);
        this.updatedDate = new Date();
    }

    /**
     * Thêm tài liệu vào bài học
     */
    /*
    public void addDocument(String documentUrl) {
        if (documentUrls == null) {
            documentUrls = new ArrayList<>();
        }
        documentUrls.add(documentUrl);
        this.updatedDate = new Date();
    }

    /**
     * Thêm mục tiêu học tập
     */
    /*
    public void addLearningObjective(String objective) {
        if (learningObjectives == null) {
            learningObjectives = new ArrayList<>();
        }
        learningObjectives.add(objective);
    }

    /**
     * Tăng số lượt xem
     */
    /*
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * Cập nhật đánh giá trung bình
     */
    /*
    public void updateAverageRating(double newRating, int totalRatings) {
        this.averageRating = ((averageRating * (totalRatings - 1)) + newRating) / totalRatings;
    }
    */
}
