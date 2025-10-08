package com.example.study_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.R;
import com.example.study_app.model.Quiz;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private Context context;
    private List<Quiz> quizzes;
    private OnQuizClickListener onQuizClickListener;

    public interface OnQuizClickListener {
        void onQuizClick(Quiz quiz);
    }

    public QuizAdapter(Context context, List<Quiz> quizzes, OnQuizClickListener listener) {
        this.context = context;
        this.quizzes = quizzes;
        this.onQuizClickListener = listener;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);

        holder.titleTextView.setText(quiz.getTitle());
        holder.descriptionTextView.setText(quiz.getDescription());
        holder.questionCountTextView.setText(quiz.getQuestionCount() + " câu hỏi");
        holder.timeLimitTextView.setText(quiz.getTimeLimit() + " phút");

        holder.startQuizButton.setOnClickListener(v -> {
            if (onQuizClickListener != null) {
                onQuizClickListener.onQuizClick(quiz);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView questionCountTextView;
        TextView timeLimitTextView;
        Button startQuizButton;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewQuizTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewQuizDescription);
            questionCountTextView = itemView.findViewById(R.id.textViewQuestionCount);
            timeLimitTextView = itemView.findViewById(R.id.textViewTimeLimit);
            startQuizButton = itemView.findViewById(R.id.buttonStartQuiz);
        }
    }
}
