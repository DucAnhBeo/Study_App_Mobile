package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study_app.fragments.ChatFragment;
import com.example.study_app.fragments.ClassroomFragment;
import com.example.study_app.fragments.DiscussionFragment;
import com.example.study_app.fragments.HomeFragment;
import com.example.study_app.fragments.QuizFragment;

public class MainActivityNew extends AppCompatActivity {
    private int userId;
    private String username;
    private String fullName;
    private TextView textViewUsername;

    private LinearLayout buttonHome;
    private LinearLayout buttonClassroom;
    private LinearLayout buttonDiscussion;
    private LinearLayout buttonQuiz;
    private LinearLayout buttonChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        // Xử lý system insets để tránh navigation bar bị che khuất
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Áp dụng padding cho navigation bar
            LinearLayout bottomNav = findViewById(R.id.bottomNavigationLayout);
            if (bottomNav != null) {
                bottomNav.setPadding(
                        bottomNav.getPaddingLeft(),
                        bottomNav.getPaddingTop(),
                        bottomNav.getPaddingRight(),
                        Math.max(20, systemBars.bottom) // Tối thiểu 20dp, hoặc theo system bar
                );
            }

            return insets;
        });

        // Get user data from intent
        userId = getIntent().getIntExtra("userId", -1);
        username = getIntent().getStringExtra("username");
        fullName = getIntent().getStringExtra("fullName");

        // Initialize views
        initializeViews();
        setupHeader();
        setupNavigationBar();

        // Load Home fragment by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            setActiveNavigation(buttonHome);
        }
    }

    private void initializeViews() {
        // Header views
        textViewUsername = findViewById(R.id.textViewUsername);
        ImageButton buttonSettings = findViewById(R.id.buttonSettings);

        // Navigation bar views
        buttonHome = findViewById(R.id.buttonHome);
        buttonClassroom = findViewById(R.id.buttonClassroom);
        buttonDiscussion = findViewById(R.id.buttonDiscussion);
        buttonQuiz = findViewById(R.id.buttonQuiz);
        buttonChatbot = findViewById(R.id.buttonChatbot);

        buttonSettings.setOnClickListener(v -> showSettingsDialog());
    }

    private void setupHeader() {
        if (fullName != null && !fullName.isEmpty()) {
            textViewUsername.setText(fullName);
        } else if (username != null && !username.isEmpty()) {
            textViewUsername.setText(username);
        } else {
            textViewUsername.setText("admin");
        }

        TextView textViewRole = findViewById(R.id.textViewRole);
        textViewRole.setText("Học sinh");
    }

    private void setupNavigationBar() {
        buttonHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            setActiveNavigation(buttonHome);
        });

        buttonClassroom.setOnClickListener(v -> {
            loadFragment(new ClassroomFragment());
            setActiveNavigation(buttonClassroom);
        });

        buttonDiscussion.setOnClickListener(v -> {
            loadFragment(new DiscussionFragment());
            setActiveNavigation(buttonDiscussion);
        });

        buttonQuiz.setOnClickListener(v -> {
            loadFragment(new QuizFragment());
            setActiveNavigation(buttonQuiz);
        });

        buttonChatbot.setOnClickListener(v -> {
            loadFragment(new ChatFragment());
            setActiveNavigation(buttonChatbot);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void setActiveNavigation(LinearLayout activeButton) {
        // Reset all navigation buttons
        resetNavigationButtons();

        // Set active state for selected button với animation
        activeButton.setSelected(true);
        activeButton.setAlpha(1.0f);

        // Animate active icon với hiệu ứng scale
        animateIconCard(activeButton, true);
    }

    private void resetNavigationButtons() {
        buttonHome.setSelected(false);
        buttonClassroom.setSelected(false);
        buttonDiscussion.setSelected(false);
        buttonQuiz.setSelected(false);
        buttonChatbot.setSelected(false);

        buttonHome.setAlpha(0.7f);
        buttonClassroom.setAlpha(0.7f);
        buttonDiscussion.setAlpha(0.7f);
        buttonQuiz.setAlpha(0.7f);
        buttonChatbot.setAlpha(0.7f);

        // Reset all icon cards
        animateIconCard(buttonHome, false);
        animateIconCard(buttonClassroom, false);
        animateIconCard(buttonDiscussion, false);
        animateIconCard(buttonQuiz, false);
        animateIconCard(buttonChatbot, false);
    }

    private void animateIconCard(LinearLayout navButton, boolean isActive) {
        // Tìm icon card, icon và label trong nav button
        androidx.cardview.widget.CardView iconCard = null;
        TextView label = null;
        ImageView icon = null;

        if (navButton == buttonHome) {
            iconCard = findViewById(R.id.homeIconCard);
            label = findViewById(R.id.homeLabel);
            icon = findViewById(R.id.homeIcon);
        } else if (navButton == buttonClassroom) {
            iconCard = findViewById(R.id.classroomIconCard);
            label = findViewById(R.id.classroomLabel);
            icon = iconCard.findViewById(android.R.id.icon);
        } else if (navButton == buttonDiscussion) {
            iconCard = findViewById(R.id.discussionIconCard);
            label = findViewById(R.id.discussionLabel);
            icon = iconCard.findViewById(android.R.id.icon);
        } else if (navButton == buttonQuiz) {
            iconCard = findViewById(R.id.quizIconCard);
            label = findViewById(R.id.quizLabel);
            icon = iconCard.findViewById(android.R.id.icon);
        } else if (navButton == buttonChatbot) {
            iconCard = findViewById(R.id.chatbotIconCard);
            label = findViewById(R.id.chatbotLabel);
            icon = iconCard.findViewById(android.R.id.icon);
        }

        if (iconCard != null && label != null) {
            if (isActive) {
                // Animation cho active state
                iconCard.animate()
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(200)
                        .start();

                iconCard.setCardBackgroundColor(getColor(R.color.nav_icon_background));

                // Cập nhật màu icon cho Home
                if (navButton == buttonHome && icon != null) {
                    icon.setColorFilter(getColor(R.color.text_white));
                }

                label.animate()
                        .alpha(1.0f)
                        .setDuration(200)
                        .start();

                // Thêm hiệu ứng bounce nhẹ
                iconCard.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setStartDelay(200)
                        .setDuration(100)
                        .start();

            } else {
                // Animation cho inactive state
                iconCard.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(150)
                        .start();

                iconCard.setCardBackgroundColor(getColor(android.R.color.transparent));

                // Cập nhật màu icon cho Home về màu primary khi inactive
                if (navButton == buttonHome && icon != null) {
                    icon.setColorFilter(getColor(R.color.primary_color));
                }

                label.animate()
                        .alpha(0.6f)
                        .setDuration(150)
                        .start();
            }
        }
    }

    private void showSettingsDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Tùy chọn")
                .setItems(new String[]{"Chỉnh sửa thông tin", "Đăng xuất"}, (dialog, which) -> {
                    if (which == 0) {
                        // Edit Profile
                        Intent intent = new Intent(this, EditProfileActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", username);
                        intent.putExtra("fullName", fullName);
                        startActivity(intent);
                    } else if (which == 1) {
                        // Logout
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}
