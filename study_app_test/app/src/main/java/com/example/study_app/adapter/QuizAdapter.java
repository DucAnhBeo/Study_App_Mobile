package com.example.study_app.adapter;

/**
 * QuizAdapter - Adapter cho RecyclerView hiển thị danh sách Quiz
 *
 * TRẠNG THÁI: ĐANG PHÁT TRIỂN - CHƯA HOÀN THÀNH
 *
 * Mục đích: Hiển thị danh sách quiz trong RecyclerView với giao diện người dùng thân thiện
 *
 * Chức năng dự kiến trong tương lai:
 * - Hiển thị thông tin quiz: tiêu đề, mô tả, số câu hỏi, thời gian
 * - Hiển thị độ khó quiz với màu sắc khác nhau
 * - Button bắt đầu làm quiz
 * - Hiển thị trạng thái đã làm/chưa làm
 * - Hiển thị điểm cao nhất nếu đã làm
 * - Animation khi click vào item
 * - Filter theo môn học, lớp, độ khó
 *
 * Kiến trúc:
 * - Kế thừa RecyclerView.Adapter
 * - QuizViewHolder để hold views của từng item
 * - OnQuizClickListener interface để handle click events
 * - Layout: item_quiz.xml
 *
 * Tích hợp:
 * - Nhận List<Quiz> từ QuizDataManager
 * - Callback tới QuizActivity khi user click "Bắt đầu làm"
 * - Chuyển tới QuizTakeActivity với quiz_id
 *
 * Hiện tại: Code được comment để tránh lỗi compile
 */

// TODO: Phát triển chức năng QuizAdapter trong tương lai
// Adapter này sẽ hiển thị danh sách quiz trong RecyclerView
// Hiện tại đang được comment để tránh lỗi compile

/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.R;
import com.example.study_app.model.Quiz;
import java.util.List;

/**
 * QuizAdapter Implementation - Sẽ được uncomment khi phát triển
 */
/*
public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    // ========== THUỘC TÍNH ==========
    private Context context;                    // Context của Activity
    private List<Quiz> quizzes;                // Danh sách quiz
    private OnQuizClickListener onQuizClickListener; // Listener cho click events

    // ========== INTERFACE ==========
    /**
     * Interface để handle click events từ quiz items
     */
    /*
    public interface OnQuizClickListener {
        void onQuizClick(Quiz quiz);           // Khi click "Bắt đầu làm"
        void onQuizLongClick(Quiz quiz);       // Khi long click (xem chi tiết)
    }

    // ========== CONSTRUCTOR ==========
    /**
     * Constructor cho QuizAdapter
     * @param context Context của Activity
     * @param quizzes Danh sách quiz cần hiển thị
     * @param listener Listener để handle click events
     */
    /*
    public QuizAdapter(Context context, List<Quiz> quizzes, OnQuizClickListener listener) {
        this.context = context;
        this.quizzes = quizzes;
        this.onQuizClickListener = listener;
    }

    // ========== RECYCLERVIEW METHODS ==========

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item quiz
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);

        // Set dữ liệu cho views
        holder.titleTextView.setText(quiz.getTitle());
        holder.descriptionTextView.setText(quiz.getDescription());
        holder.questionCountTextView.setText(quiz.getQuestionCount() + " câu hỏi");
        holder.timeLimitTextView.setText(quiz.getTimeLimit() + " phút");
        holder.subjectTextView.setText(quiz.getSubject());
        holder.gradeTextView.setText(quiz.getGrade());

        // Set màu cho độ khó
        setDifficultyColor(holder.difficultyTextView, quiz.getDifficulty());

        // Set click listeners
        holder.startQuizButton.setOnClickListener(v -> {
            if (onQuizClickListener != null) {
                onQuizClickListener.onQuizClick(quiz);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onQuizClickListener != null) {
                onQuizClickListener.onQuizLongClick(quiz);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return quizzes != null ? quizzes.size() : 0;
    }

    // ========== HELPER METHODS ==========

    /**
     * Set màu sắc cho TextView độ khó dựa trên level
     */
    /*
    private void setDifficultyColor(TextView textView, String difficulty) {
        textView.setText(difficulty);

        switch (difficulty.toLowerCase()) {
            case "dễ":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                break;
            case "trung bình":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
                break;
            case "khó":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                break;
            default:
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
    }

    /**
     * Cập nhật danh sách quiz mới
     */
    /*
    public void updateQuizzes(List<Quiz> newQuizzes) {
        this.quizzes = newQuizzes;
        notifyDataSetChanged();
    }

    /**
     * Thêm quiz mới vào danh sách
     */
    /*
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        notifyItemInserted(quizzes.size() - 1);
    }

    /**
     * Xóa quiz khỏi danh sách
     */
    /*
    public void removeQuiz(int position) {
        quizzes.remove(position);
        notifyItemRemoved(position);
    }

    // ========== VIEWHOLDER CLASS ==========

    /**
     * ViewHolder cho từng item quiz
     * Chứa tất cả views cần thiết để hiển thị thông tin quiz
     */
    /*
    static class QuizViewHolder extends RecyclerView.ViewHolder {
        // Text views
        TextView titleTextView;              // Tiêu đề quiz
        TextView descriptionTextView;        // Mô tả quiz
        TextView questionCountTextView;      // Số lượng câu hỏi
        TextView timeLimitTextView;          // Thời gian làm bài
        TextView subjectTextView;            // Môn học
        TextView gradeTextView;              // Lớp
        TextView difficultyTextView;         // Độ khó
        TextView statusTextView;             // Trạng thái (đã làm/chưa làm)
        TextView highScoreTextView;          // Điểm cao nhất

        // Buttons
        Button startQuizButton;              // Button bắt đầu làm quiz
        Button reviewButton;                 // Button xem lại (nếu đã làm)

        /**
         * Constructor cho ViewHolder
         */
        /*
        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            titleTextView = itemView.findViewById(R.id.textViewQuizTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewQuizDescription);
            questionCountTextView = itemView.findViewById(R.id.textViewQuestionCount);
            timeLimitTextView = itemView.findViewById(R.id.textViewTimeLimit);
            subjectTextView = itemView.findViewById(R.id.textViewSubject);
            gradeTextView = itemView.findViewById(R.id.textViewGrade);
            difficultyTextView = itemView.findViewById(R.id.textViewDifficulty);
            statusTextView = itemView.findViewById(R.id.textViewStatus);
            highScoreTextView = itemView.findViewById(R.id.textViewHighScore);

            startQuizButton = itemView.findViewById(R.id.buttonStartQuiz);
            reviewButton = itemView.findViewById(R.id.buttonReview);
        }
    }
}
*/
