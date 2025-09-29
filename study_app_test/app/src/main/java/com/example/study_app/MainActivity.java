package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    // User information
    private int userId;
    private String username;
    private String fullName;
    private TextView textViewUsername;
    private ActivityResultLauncher<Intent> editProfileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_main);

        // Initialize Activity Result Launcher
        editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String updatedUsername = data.getStringExtra("updatedUsername");
                        String updatedFullName = data.getStringExtra("updatedFullName");

                        if (updatedUsername != null) {
                            username = updatedUsername;
                            textViewUsername.setText(username);
                        }

                        if (updatedFullName != null) {
                            fullName = updatedFullName;
                        }
                    }
                }
            }
        );

        // Get user data from intent
        userId = getIntent().getIntExtra("userId", -1);
        username = getIntent().getStringExtra("username");
        fullName = getIntent().getStringExtra("fullName");

        // Initialize views
        selectedBookLayout = findViewById(R.id.selectedBookLayout);
        textViewSelectedInfo = findViewById(R.id.textViewSelectedInfo);
        buttonEnterClassroom = findViewById(R.id.buttonEnterClassroom);

        // Header: set username and role
        textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewRole = findViewById(R.id.textViewRole);
        if (fullName != null && !fullName.isEmpty()) {
            textViewUsername.setText(fullName);
        } else if (username != null && !username.isEmpty()) {
            textViewUsername.setText(username);
        }else {
            textViewUsername.setText("admin");
        }
        textViewRole.setText("Học sinh");

        // Settings button
        ImageButton buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Tùy chọn")
                .setItems(new String[]{"Chỉnh sửa thông tin", "Đăng xuất"}, (dialog, which) -> {
                    if (which == 0) {
                        // Edit Profile
                        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", username);
                        intent.putExtra("fullName", fullName);
                        editProfileLauncher.launch(intent);
                    } else if (which == 1) {
                        // Logout
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
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
            // TODO: Phát triển chức năng Classroom trong tương lai
            // Chức năng này sẽ bao gồm:
            // - Quản lý bài học (Lessons) theo môn học và lớp
            // - Video bài giảng và tài liệu
            // - Theo dõi tiến độ học tập
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Classroom đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
        });

        // Footer navigation buttons
        LinearLayout buttonHome = findViewById(R.id.buttonHome);
        LinearLayout buttonClassroom = findViewById(R.id.buttonClassroom);
        LinearLayout buttonDiscussion = findViewById(R.id.buttonDiscussion);
        LinearLayout buttonQuiz = findViewById(R.id.buttonQuiz);
        LinearLayout buttonChatbot = findViewById(R.id.buttonChatbot);

        buttonHome.setOnClickListener(v -> {
            // Already at Home
        });

        buttonClassroom.setOnClickListener(v -> {
            // TODO: Phát triển chức năng Classroom trong tương lai
            // Chức năng này sẽ bao gồm:
            // - Quản lý bài học (Lessons) theo môn học và lớp
            // - Video bài giảng và tài liệu
            // - Theo dõi tiến độ học tập
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Classroom đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
        });

        buttonDiscussion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
            startActivity(intent);
        });

        buttonQuiz.setOnClickListener(v -> {
            // TODO: Phát triển chức năng Quiz trong tương lai
            // Chức năng này sẽ bao gồm:
            // - Tạo và quản lý câu hỏi trắc nghiệm
            // - Hệ thống làm bài và chấm điểm tự động
            // - Theo dõi kết quả và thống kê điểm số
            // - Phân loại câu hỏi theo độ khó và chủ đề
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Quiz đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
        });

        buttonChatbot.setOnClickListener(v -> {
            // TODO: Phát triển chức năng Chatbot trong tương lai
            // Chức năng này sẽ bao gồm:
            // - AI Assistant hỗ trợ học tập
            // - Trả lời câu hỏi về bài học
            // - Gợi ý và hướng dẫn học tập
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Chatbot đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
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
