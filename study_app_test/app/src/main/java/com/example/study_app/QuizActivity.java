package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
// TODO: Uncomment when Quiz feature is implemented
// import com.example.study_app.adapter.QuizAdapter;
// import com.example.study_app.data.QuizDataManager;
// import com.example.study_app.model.Quiz;
import java.util.List;

/**
 * QuizActivity - Màn hình hiển thị danh sách Quiz
 *
 * TRẠNG THÁI: ĐANG PHÁT TRIỂN - CHƯA HOÀN THÀNH
 *
 * Chức năng dự kiến trong tương lai:
 * - Hiển thị danh sách quiz theo lớp và sách giáo khoa
 * - Cho phép học sinh chọn và làm quiz
 * - Theo dõi tiến độ và kết quả làm bài
 * - Phân loại quiz theo độ khó (Dễ, Trung bình, Khó)
 * - Hiển thị thống kê điểm số và thành tích
 * - Tích hợp với backend để lưu trữ câu hỏi và kết quả
 *
 * Kiến trúc dự kiến:
 * - RecyclerView hiển thị danh sách quiz
 * - QuizAdapter để render từng item quiz
 * - QuizDataManager để quản lý dữ liệu
 * - Kết nối với MySQL database để lưu trữ
 *
 * Hiện tại: Chỉ hiển thị thông báo placeholder
 */
public class QuizActivity extends AppCompatActivity {
    // Views - Các thành phần giao diện
    private RecyclerView recyclerView;        // Danh sách quiz (tương lai)
    private TextView textViewTitle;           // Tiêu đề màn hình
    private TextView textViewEmptyState;      // Thông báo trống/placeholder

    // TODO: Uncomment when Quiz feature is implemented
    // Data components - Các thành phần dữ liệu (tương lai)
    // private QuizAdapter adapter;           // Adapter để hiển thị danh sách
    // private List<Quiz> quizzes;            // Danh sách quiz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Khởi tạo views
        initializeViews();

        // TODO: Implement Quiz functionality in the future
        // Hiện tại chỉ hiển thị thông báo placeholder
        showPlaceholderMessage();

        // TODO: Uncomment và implement khi Quiz feature đã sẵn sàng
        // loadQuizData();
    }

    /**
     * Khởi tạo các view components
     */
    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewQuizzes);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewEmptyState = findViewById(R.id.textViewEmptyState);
    }

    /**
     * Hiển thị thông báo placeholder cho đến khi tính năng được phát triển
     */
    private void showPlaceholderMessage() {
        textViewTitle.setText("Quiz - Đang phát triển");
        textViewEmptyState.setText("Chức năng Quiz sẽ được phát triển trong tương lai\n\n" +
                "Các tính năng dự kiến:\n" +
                "• Trắc nghiệm theo từng môn học\n" +
                "• Chấm điểm tự động\n" +
                "• Theo dõi tiến độ học tập\n" +
                "• Thống kê kết quả\n" +
                "• Phân loại theo độ khó");
        textViewEmptyState.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    /* ========== CÁC PHƯƠNG THỨC TƯƠNG LAI ========== */
    /* TODO: Uncomment và implement khi Quiz feature được phát triển

    /**
     * Load dữ liệu quiz dựa trên lớp và sách giáo khoa được chọn
     */
    /*
    private void loadQuizData() {
        // Lấy thông tin lớp và sách từ intent
        String selectedGrade = getIntent().getStringExtra("grade");
        String selectedTextbook = getIntent().getStringExtra("textbook");

        if (selectedGrade != null && selectedTextbook != null) {
            textViewTitle.setText("Quiz " + selectedGrade + " - " + selectedTextbook);
            loadQuizzesForGradeAndTextbook(selectedGrade, selectedTextbook);
        } else {
            textViewTitle.setText("Quiz - Tất cả");
            loadAllQuizzes();
        }
    }

    /**
     * Load quiz cho lớp và sách cụ thể
     */
    /*
    private void loadQuizzesForGradeAndTextbook(String grade, String textbook) {
        quizzes = QuizDataManager.getQuizzes(grade, textbook);
        setupRecyclerView();
    }

    /**
     * Load tất cả quiz available
     */
    /*
    private void loadAllQuizzes() {
        // Mặc định load Lớp 6 - Cánh diều làm ví dụ
        quizzes = QuizDataManager.getQuizzes("Lớp 6", "Cánh diều");
        setupRecyclerView();
    }

    /**
     * Setup RecyclerView để hiển thị danh sách quiz
     */
    /*
    private void setupRecyclerView() {
        if (quizzes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyState.setVisibility(View.VISIBLE);
            textViewEmptyState.setText("Không có quiz nào cho lựa chọn này");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewEmptyState.setVisibility(View.GONE);

            adapter = new QuizAdapter(this, quizzes, this::startQuiz);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * Bắt đầu làm quiz - chuyển sang QuizTakeActivity
     */
    /*
    private void startQuiz(Quiz quiz) {
        Intent intent = new Intent(this, QuizTakeActivity.class);
        intent.putExtra("quiz_id", quiz.getId());
        intent.putExtra("quiz_title", quiz.getTitle());
        startActivity(intent);
    }
    */
}
