package com.example.study_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {
    private String selectedGrade = "";
    private String selectedTextbook = "";
    private CardView selectedBookLayout;
    private TextView textViewSelectedInfo;
    private Button buttonEnterClassroom;
    private Button currentGradeButton = null;
    private Button currentTextbookButton = null;

    // User information
    private int userId;
    private String username;
    private String fullName;
    private TextView textViewUsername;
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private ImageView imageViewBook;
    private String selectedTitle = null;
    private String selectedJsonAssetPath = null;
    private String selectedPdfUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_main);


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
        imageViewBook = findViewById(R.id.imageViewBook);


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

        imageViewBook.setOnClickListener(v -> OpenReaderIfReady());

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
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Classroom đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
        });

        buttonDiscussion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        buttonQuiz.setOnClickListener(v -> {
            android.widget.Toast.makeText(MainActivity.this, "Chức năng Quiz đang được phát triển", android.widget.Toast.LENGTH_SHORT).show();
        });

        buttonChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Điều chỉnh layout để ko bị chồng
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
            // Thay đổi màu nền thành primary và chữ thành trắng để tương phản
            clickedButton.setBackgroundTintList(getColorStateList(R.color.primary_color));
            clickedButton.setTextColor(getColor(R.color.text_white));
            currentTextbookButton = clickedButton;
        }

        updateBookDisplay();
    }

    private void OpenReaderIfReady() {
        Intent i = new Intent(MainActivity.this, ReaderActivity.class);
        i.putExtra("title", selectedTitle);
        i.putExtra("pdf_url", selectedPdfUrl);
        i.putExtra("jsonAssetPath", selectedJsonAssetPath);
        startActivity(i);
    }

    private void updateBookDisplay() {
        if (!selectedGrade.isEmpty() && !selectedTextbook.isEmpty()) {
            selectedBookLayout.setVisibility(View.VISIBLE);
            textViewSelectedInfo.setText(selectedGrade + "\n" + selectedTextbook);

            loadCoverImage(selectedGrade, selectedTextbook);

            imageViewBook.setContentDescription(selectedGrade + " - " + selectedTextbook);
            selectedTitle = selectedGrade + " - " + selectedTextbook;
            selectedJsonAssetPath = buildTocAssetPath(selectedGrade, selectedTextbook);

            String rawPdf = loadPdfUrlFromJson(selectedJsonAssetPath);
            selectedPdfUrl = rawPdf;

        } else {
            selectedBookLayout.setVisibility(View.GONE);
            selectedTitle = null;
            selectedJsonAssetPath = null;
            selectedPdfUrl = null;
        }
    }

    private void loadCoverImage(String grade, String textbook) {
        String coverAssetPath = buildCoverAssetPath(grade, textbook);
        try (InputStream is = getAssets().open(coverAssetPath)) {
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp != null) {
                imageViewBook.setImageBitmap(bmp);
            } else {
                imageViewBook.setImageResource(R.drawable.book_image);
            }
        } catch (IOException e) {
            imageViewBook.setImageResource(R.drawable.book_image);
        }
    }

    private String mapSeriesCode(String textbook) {
        switch (textbook) {
            case "Cánh diều": return "CD";
            case "Chân trời sáng tạo": return "CTST";
            case "Kết nối tri thức": return "KNTT";
            default: return textbook;
        }
    }
    private String extractGradeNumber(String grade) {
        return grade.replace("Lớp", "").trim();
    }

    private String buildCoverAssetPath(String grade, String textbook) {
        String series = mapSeriesCode(textbook);
        String g = extractGradeNumber(grade);
        String fileName = series + g + ".png";
        return "cover_sgk/" + series + "/" + fileName;
    }

    private String buildTocAssetPath(String grade, String textbook) {
        String series = mapSeriesCode(textbook);
        String g = extractGradeNumber(grade);
        String fileName = series + g + ".json";
        return "table_of_content/" + series + "/" + fileName;
    }

    private String loadPdfUrlFromJson(String assetPath) {
        try (InputStream is = getAssets().open(assetPath)) {
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            String json = new BufferedReader(reader).lines().collect(Collectors.joining());
            return new JSONObject(json).getString("pdf_url");
        } catch (Exception e) {
            return null;
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

        // Reset về trạng thái ban đầu với màu primary và nền trong suốt
        buttonCanhDieu.setSelected(false);
        buttonCanhDieu.setTextColor(getColor(R.color.primary_color));
        buttonCanhDieu.setBackgroundTintList(getColorStateList(android.R.color.transparent));

        buttonChanTroi.setSelected(false);
        buttonChanTroi.setTextColor(getColor(R.color.primary_color));
        buttonChanTroi.setBackgroundTintList(getColorStateList(android.R.color.transparent));

        buttonKetNoi.setSelected(false);
        buttonKetNoi.setTextColor(getColor(R.color.primary_color));
        buttonKetNoi.setBackgroundTintList(getColorStateList(android.R.color.transparent));
    }
}
