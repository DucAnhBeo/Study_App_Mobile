package com.example.study_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.study_app.data.QuizDataManager;
import com.example.study_app.model.Question;
import com.example.study_app.model.Quiz;
import java.util.ArrayList;
import java.util.List;

public class QuizTakeActivity extends AppCompatActivity {
    private TextView textViewQuizTitle;
    private TextView textViewProgress;
    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private RadioButton option1, option2, option3, option4;
    private Button buttonPrev, buttonNext, buttonSubmit;

    private Quiz quiz;
    private int currentIndex = 0;
    private List<Integer> selectedIndices; // -1 for not selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_take);

        String quizId = getIntent().getStringExtra("quiz_id");
        quiz = QuizDataManager.getQuizById(quizId);
        if (quiz == null) {
            finish();
            return;
        }

        bindViews();
        selectedIndices = new ArrayList<>();
        for (int i = 0; i < quiz.getQuestions().size(); i++) selectedIndices.add(-1);

        textViewQuizTitle.setText(quiz.getTitle());
        showQuestion();

        buttonPrev.setOnClickListener(v -> {
            saveSelection();
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion();
            }
        });
        buttonNext.setOnClickListener(v -> {
            saveSelection();
            if (currentIndex < quiz.getQuestions().size() - 1) {
                currentIndex++;
                showQuestion();
            }
        });
        buttonSubmit.setOnClickListener(v -> {
            saveSelection();
            submitQuiz();
        });
    }

    private void bindViews() {
        textViewQuizTitle = findViewById(R.id.textViewQuizTitle);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        option1 = findViewById(R.id.radioOption1);
        option2 = findViewById(R.id.radioOption2);
        option3 = findViewById(R.id.radioOption3);
        option4 = findViewById(R.id.radioOption4);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonNext = findViewById(R.id.buttonNext);
        buttonSubmit = findViewById(R.id.buttonSubmit);
    }

    private void showQuestion() {
        Question q = quiz.getQuestions().get(currentIndex);
        textViewProgress.setText("Câu " + (currentIndex + 1) + "/" + quiz.getQuestions().size());
        textViewQuestion.setText(q.getQuestionText());

        List<String> options = q.getOptions();
        option1.setText(options.size() > 0 ? options.get(0) : "");
        option2.setText(options.size() > 1 ? options.get(1) : "");
        option3.setText(options.size() > 2 ? options.get(2) : "");
        option4.setText(options.size() > 3 ? options.get(3) : "");

        radioGroupOptions.clearCheck();
        int sel = selectedIndices.get(currentIndex);
        if (sel == 0) option1.setChecked(true);
        else if (sel == 1) option2.setChecked(true);
        else if (sel == 2) option3.setChecked(true);
        else if (sel == 3) option4.setChecked(true);

        buttonPrev.setEnabled(currentIndex > 0);
        buttonNext.setEnabled(currentIndex < quiz.getQuestions().size() - 1);
        buttonSubmit.setVisibility(currentIndex == quiz.getQuestions().size() - 1 ? View.VISIBLE : View.GONE);
    }

    private void saveSelection() {
        int checkedId = radioGroupOptions.getCheckedRadioButtonId();
        int idx = -1;
        if (checkedId == R.id.radioOption1) idx = 0;
        else if (checkedId == R.id.radioOption2) idx = 1;
        else if (checkedId == R.id.radioOption3) idx = 2;
        else if (checkedId == R.id.radioOption4) idx = 3;
        selectedIndices.set(currentIndex, idx);
    }

    private void submitQuiz() {
        int correct = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question q = quiz.getQuestions().get(i);
            int sel = selectedIndices.get(i);
            boolean ok = sel == q.getCorrectAnswerIndex();
            if (ok) correct++;
            sb.append("Câu ").append(i + 1).append(ok ? ": Đúng" : ": Sai").append("\n")
              .append("- Đáp án đúng: ").append(q.getCorrectAnswer()).append("\n");
            if (q.getExplanation() != null && !q.getExplanation().isEmpty()) {
                sb.append("- Giải thích: ").append(q.getExplanation()).append("\n");
            }
            sb.append("\n");
        }
        int total = quiz.getQuestions().size();
        new AlertDialog.Builder(this)
                .setTitle("Kết quả: " + correct + "/" + total)
                .setMessage(sb.toString())
                .setPositiveButton("Đóng", (d, w) -> finish())
                .show();
    }
}

