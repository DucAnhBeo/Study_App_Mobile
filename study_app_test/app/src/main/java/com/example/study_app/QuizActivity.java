package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.adapter.QuizAdapter;
import com.example.study_app.data.QuizDataManager;
import com.example.study_app.model.Quiz;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textViewTitle;
    private TextView textViewEmptyState;
    private QuizAdapter adapter;
    private List<Quiz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewQuizzes);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewEmptyState = findViewById(R.id.textViewEmptyState);

        // Get grade and textbook from intent (if available)
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

    private void loadQuizzesForGradeAndTextbook(String grade, String textbook) {
        quizzes = QuizDataManager.getQuizzes(grade, textbook);
        setupRecyclerView();
    }

    private void loadAllQuizzes() {
        // For now, load a default set (Lớp 6 - Cánh diều as example)
        quizzes = QuizDataManager.getQuizzes("Lớp 6", "Cánh diều");
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (quizzes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewEmptyState.setVisibility(View.GONE);

            adapter = new QuizAdapter(this, quizzes, this::startQuiz);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    private void startQuiz(Quiz quiz) {
        Intent intent = new Intent(this, QuizTakeActivity.class);
        intent.putExtra("quiz_id", quiz.getId());
        startActivity(intent);
    }
}
