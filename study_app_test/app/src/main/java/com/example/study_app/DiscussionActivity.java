package com.example.study_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.study_app.adapter.DiscussionAdapter;
import com.example.study_app.data.DiscussionDataManager;
import com.example.study_app.model.Discussion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiscussionAdapter adapter;
    private List<Discussion> discussions;
    private List<Discussion> filteredDiscussions;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private TextView textViewNoResults;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int currentUserId;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        // Lấy thông tin user từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        currentUserId = prefs.getInt("user_id", -1);
        currentUsername = prefs.getString("username", "");

        if (currentUserId == -1) {
            Toast.makeText(this, "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewDiscussions);
        FloatingActionButton fabAddQuestion = findViewById(R.id.fabAddQuestion);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonClearSearch = findViewById(R.id.buttonClearSearch);
        textViewNoResults = findViewById(R.id.textViewNoResults);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Setup RecyclerView
        discussions = new ArrayList<>();
        filteredDiscussions = new ArrayList<>();
        adapter = new DiscussionAdapter(this, filteredDiscussions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this::loadDiscussions);

        // Setup search functionality
        setupSearchFeature();

        // Setup FAB for adding new question
        fabAddQuestion.setOnClickListener(v -> showAddQuestionDialog());

        // Load discussions from API
        loadDiscussions();
    }

    private void loadDiscussions() {
        swipeRefreshLayout.setRefreshing(true);

        DiscussionDataManager.getAllDiscussions(new DiscussionDataManager.DiscussionCallback() {
            @Override
            public void onSuccess(List<Discussion> discussionList) {
                runOnUiThread(() -> {
                    discussions.clear();
                    discussions.addAll(discussionList);
                    filterDiscussions(editTextSearch.getText().toString());
                    swipeRefreshLayout.setRefreshing(false);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                });
            }
        });
    }

    private void setupSearchFeature() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDiscussions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Show/hide clear button
                if (s.length() > 0) {
                    buttonClearSearch.setVisibility(View.VISIBLE);
                } else {
                    buttonClearSearch.setVisibility(View.GONE);
                }
            }
        });

        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
            editTextSearch.clearFocus();
        });
    }

    private void filterDiscussions(String query) {
        filteredDiscussions.clear();

        if (query.trim().isEmpty()) {
            // Show all discussions if search is empty
            filteredDiscussions.addAll(discussions);
        } else {
            // Filter discussions based on question content
            String lowerQuery = query.toLowerCase().trim();
            for (Discussion discussion : discussions) {
                if (discussion.getQuestion().toLowerCase().contains(lowerQuery) ||
                    discussion.getAuthor().toLowerCase().contains(lowerQuery)) {
                    filteredDiscussions.add(discussion);
                }
            }
        }

        // Update UI based on results
        if (filteredDiscussions.isEmpty() && !query.trim().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textViewNoResults.setVisibility(View.VISIBLE);
            textViewNoResults.setText("Không tìm thấy kết quả cho: " + query);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewNoResults.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }

    private void showAddQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tạo câu hỏi mới");

        // Create EditText for question input
        final EditText input = new EditText(this);
        input.setHint("Nhập câu hỏi của bạn...");
        input.setMinLines(3);
        builder.setView(input);

        builder.setPositiveButton("Đăng câu hỏi", (dialog, which) -> {
            String question = input.getText().toString().trim();
            if (!question.isEmpty()) {
                createQuestion(question);
            } else {
                Toast.makeText(this, "Vui lòng nhập câu hỏi", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void createQuestion(String content) {
        DiscussionDataManager.createQuestion(currentUserId, content, new DiscussionDataManager.ActionCallback() {
            @Override
            public void onSuccess(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, message, Toast.LENGTH_SHORT).show();
                    // Reload discussions after creating new question
                    loadDiscussions();
                    editTextSearch.setText("");
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Method để gọi từ adapter khi cần tạo answer
    public void createAnswer(int questionId, String content) {
        DiscussionDataManager.createAnswer(questionId, currentUserId, content, new DiscussionDataManager.ActionCallback() {
            @Override
            public void onSuccess(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, message, Toast.LENGTH_SHORT).show();
                    // Reload discussions after creating answer
                    loadDiscussions();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Method để gọi từ adapter khi cần xóa question
    public void deleteQuestion(int questionId) {
        DiscussionDataManager.deleteQuestion(questionId, currentUserId, new DiscussionDataManager.ActionCallback() {
            @Override
            public void onSuccess(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, message, Toast.LENGTH_SHORT).show();
                    // Reload discussions after deleting question
                    loadDiscussions();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(DiscussionActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }
}
