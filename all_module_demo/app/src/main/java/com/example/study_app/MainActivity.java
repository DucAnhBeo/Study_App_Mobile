package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private String selectedGrade = "";
    private String selectedTextbook = "";
    private LinearLayout selectedBookLayout;
    private TextView textViewSelectedInfo;
    private Button buttonEnterClassroom;

    // Keep track of selected buttons for visual feedback
    private Button currentGradeButton = null;
    private Button currentTextbookButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        selectedBookLayout = findViewById(R.id.selectedBookLayout);
        textViewSelectedInfo = findViewById(R.id.textViewSelectedInfo);
        buttonEnterClassroom = findViewById(R.id.buttonEnterClassroom);

        // Header: set username and role
        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewRole = findViewById(R.id.textViewRole);
        textViewUsername.setText("admin");
        textViewRole.setText("Quản trị viên");

        // Settings button
        ImageButton buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            // TODO: Open settings activity or dialog
        });

        // Grade selection buttons
        Button buttonGrade6 = findViewById(R.id.buttonGrade6);
        Button buttonGrade7 = findViewById(R.id.buttonGrade7);
        Button buttonGrade8 = findViewById(R.id.buttonGrade8);
        Button buttonGrade9 = findViewById(R.id.buttonGrade9);

        buttonGrade6.setOnClickListener(v -> selectGrade("Lớp 6"));
        buttonGrade7.setOnClickListener(v -> selectGrade("Lớp 7"));
        buttonGrade8.setOnClickListener(v -> selectGrade("Lớp 8"));
        buttonGrade9.setOnClickListener(v -> selectGrade("Lớp 9"));

        // Textbook selection buttons
        Button buttonCanhDieu = findViewById(R.id.buttonCanhDieu);
        Button buttonChanTroi = findViewById(R.id.buttonChanTroi);
        Button buttonKetNoi = findViewById(R.id.buttonKetNoi);

        buttonCanhDieu.setOnClickListener(v -> selectTextbook("Cánh diều"));
        buttonChanTroi.setOnClickListener(v -> selectTextbook("Chân trời sáng tạo"));
        buttonKetNoi.setOnClickListener(v -> selectTextbook("Kết nối tri thức"));

        // Enter classroom button
        buttonEnterClassroom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassroomActivity.class);
            intent.putExtra("grade", selectedGrade);
            intent.putExtra("textbook", selectedTextbook);
            startActivity(intent);
        });

        // Footer navigation buttons
        LinearLayout buttonHome = findViewById(R.id.buttonHome);
        LinearLayout buttonClassroom = findViewById(R.id.buttonClassroom);
        LinearLayout buttonDiscussion = findViewById(R.id.buttonDiscussion);
        LinearLayout buttonQuiz = findViewById(R.id.buttonQuiz);
        LinearLayout buttonChatbot = findViewById(R.id.buttonChatbot);

        buttonHome.setOnClickListener(v -> {
            // TODO: Navigate to Home
        });

        buttonClassroom.setOnClickListener(v -> {
            if (!selectedGrade.isEmpty() && !selectedTextbook.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ClassroomActivity.class);
                intent.putExtra("grade", selectedGrade);
                intent.putExtra("textbook", selectedTextbook);
                startActivity(intent);
            }
        });

        buttonDiscussion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
            startActivity(intent);
        });

        buttonQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            if (!selectedGrade.isEmpty() && !selectedTextbook.isEmpty()) {
                intent.putExtra("grade", selectedGrade);
                intent.putExtra("textbook", selectedTextbook);
            }
            startActivity(intent);
        });

        buttonChatbot.setOnClickListener(v -> {
            // TODO: Navigate to Chatbot
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void selectGrade(String grade) {
        // Reset all grade buttons to default appearance first
        resetAllGradeButtons();

        selectedGrade = grade;

        // Set selected state for current grade button
        Button clickedButton = getCurrentGradeButton(grade);
        if (clickedButton != null) {
            clickedButton.setSelected(true);
            clickedButton.setTextColor(getColor(android.R.color.white));
            currentGradeButton = clickedButton;
        }

        updateBookDisplay();
    }

    private void selectTextbook(String textbook) {
        // Reset all textbook buttons to default appearance first
        resetAllTextbookButtons();

        selectedTextbook = textbook;

        // Set selected state for current textbook button
        Button clickedButton = getCurrentTextbookButton(textbook);
        if (clickedButton != null) {
            clickedButton.setSelected(true);
            clickedButton.setTextColor(getColor(android.R.color.white));
            currentTextbookButton = clickedButton;
        }

        updateBookDisplay();
    }

    private void updateBookDisplay() {
        // Only show book when BOTH grade and textbook are selected
        if (!selectedGrade.isEmpty() && !selectedTextbook.isEmpty()) {
            selectedBookLayout.setVisibility(View.VISIBLE);
            textViewSelectedInfo.setText(selectedGrade + "\n" + selectedTextbook);
        } else {
            selectedBookLayout.setVisibility(View.GONE);
        }
    }

    private Button getCurrentGradeButton(String grade) {
        switch (grade) {
            case "Lớp 6":
                return findViewById(R.id.buttonGrade6);
            case "Lớp 7":
                return findViewById(R.id.buttonGrade7);
            case "Lớp 8":
                return findViewById(R.id.buttonGrade8);
            case "Lớp 9":
                return findViewById(R.id.buttonGrade9);
            default:
                return null;
        }
    }

    private Button getCurrentTextbookButton(String textbook) {
        switch (textbook) {
            case "Cánh diều":
                return findViewById(R.id.buttonCanhDieu);
            case "Chân trời sáng tạo":
                return findViewById(R.id.buttonChanTroi);
            case "Kết nối tri thức":
                return findViewById(R.id.buttonKetNoi);
            default:
                return null;
        }
    }

    private void resetAllGradeButtons() {
        Button buttonGrade6 = findViewById(R.id.buttonGrade6);
        Button buttonGrade7 = findViewById(R.id.buttonGrade7);
        Button buttonGrade8 = findViewById(R.id.buttonGrade8);
        Button buttonGrade9 = findViewById(R.id.buttonGrade9);

        buttonGrade6.setSelected(false);
        buttonGrade6.setTextColor(getColor(android.R.color.black));
        buttonGrade7.setSelected(false);
        buttonGrade7.setTextColor(getColor(android.R.color.black));
        buttonGrade8.setSelected(false);
        buttonGrade8.setTextColor(getColor(android.R.color.black));
        buttonGrade9.setSelected(false);
        buttonGrade9.setTextColor(getColor(android.R.color.black));
    }

    private void resetAllTextbookButtons() {
        Button buttonCanhDieu = findViewById(R.id.buttonCanhDieu);
        Button buttonChanTroi = findViewById(R.id.buttonChanTroi);
        Button buttonKetNoi = findViewById(R.id.buttonKetNoi);

        buttonCanhDieu.setSelected(false);
        buttonCanhDieu.setTextColor(getColor(android.R.color.black));
        buttonChanTroi.setSelected(false);
        buttonChanTroi.setTextColor(getColor(android.R.color.black));
        buttonKetNoi.setSelected(false);
        buttonKetNoi.setTextColor(getColor(android.R.color.black));
    }
}