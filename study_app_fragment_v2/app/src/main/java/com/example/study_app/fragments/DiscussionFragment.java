package com.example.study_app.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.study_app.R;
import com.example.study_app.adapter.DiscussionAdapter;
import com.example.study_app.data.DiscussionDataManager;
import com.example.study_app.model.Discussion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DiscussionFragment extends Fragment implements DiscussionAdapter.OnDiscussionActionListener {
    private RecyclerView recyclerView;
    private DiscussionAdapter adapter;
    private List<Discussion> discussions;
    private List<Discussion> filteredDiscussions;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private LinearLayout textViewNoResults;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int currentUserId;
    private String currentUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussion, container, false);

        // Lấy thông tin user từ Activity
        if (getActivity() != null) {
            currentUserId = getActivity().getIntent().getIntExtra("userId", -1);
            currentUsername = getActivity().getIntent().getStringExtra("username");
        }

        if (currentUserId == -1) {
            Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewDiscussions);
        FloatingActionButton fabAddQuestion = view.findViewById(R.id.fabAddQuestion);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        buttonClearSearch = view.findViewById(R.id.buttonClearSearch);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        // Setup RecyclerView
        discussions = new ArrayList<>();
        filteredDiscussions = new ArrayList<>();
        adapter = new DiscussionAdapter(getContext(), filteredDiscussions);
        adapter.setActionListener(this); // Set this fragment as the action listener
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this::loadDiscussions);

        // Setup search functionality
        setupSearchFeature();

        // Setup FAB for adding new question
        fabAddQuestion.setOnClickListener(v -> showAddQuestionDialog());

        // Load discussions from API
        loadDiscussions();

        return view;
    }

    private void loadDiscussions() {
        swipeRefreshLayout.setRefreshing(true);

        DiscussionDataManager.getAllDiscussions(new DiscussionDataManager.DiscussionCallback() {
            @Override
            public void onSuccess(List<Discussion> discussionList) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        discussions.clear();
                        discussions.addAll(discussionList);
                        filterDiscussions(editTextSearch.getText().toString());
                        swipeRefreshLayout.setRefreshing(false);
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Lỗi tải dữ liệu: " + error, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    });
                }
            }
        });
    }

    private void setupSearchFeature() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                filterDiscussions(query);
                buttonClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
            buttonClearSearch.setVisibility(View.GONE);
        });
    }

    private void filterDiscussions(String query) {
        filteredDiscussions.clear();

        if (query.isEmpty()) {
            filteredDiscussions.addAll(discussions);
        } else {
            String lowercaseQuery = query.toLowerCase();
            for (Discussion discussion : discussions) {
                if (discussion.getContent().toLowerCase().contains(lowercaseQuery) ||
                    discussion.getAuthor().toLowerCase().contains(lowercaseQuery)) {
                    filteredDiscussions.add(discussion);
                }
            }
        }

        adapter.notifyDataSetChanged();
        textViewNoResults.setVisibility(filteredDiscussions.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showAddQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Đặt câu hỏi mới");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_question, null);
        EditText editTextQuestion = dialogView.findViewById(R.id.editTextQuestion);

        builder.setView(dialogView);
        builder.setPositiveButton("Đăng", (dialog, which) -> {
            String questionText = editTextQuestion.getText().toString().trim();
            if (!questionText.isEmpty()) {
                postNewQuestion(questionText);
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập nội dung câu hỏi", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);

        builder.show();
    }

    private void postNewQuestion(String questionText) {
        DiscussionDataManager.createQuestion(questionText, currentUserId, new DiscussionDataManager.DiscussionCallback() {
            @Override
            public void onSuccess(List<Discussion> discussionList) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Đã đăng câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                        loadDiscussions();
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Lỗi đăng câu hỏi: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    // Implement interface methods
    @Override
    public void onCreateAnswer(int discussionId, String content) {
        if (currentUserId == -1) {
            Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
            return;
        }

        DiscussionDataManager.createAnswer(discussionId, currentUserId, content, new DiscussionDataManager.ActionCallback() {
            @Override
            public void onSuccess(String message) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Đã thêm câu trả lời thành công!", Toast.LENGTH_SHORT).show();
                        // Reload discussions để hiển thị câu trả lời mới
                        loadDiscussions();
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Lỗi thêm câu trả lời: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    @Override
    public void onDeleteQuestion(int questionId) {
        if (currentUserId == -1) {
            Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
            return;
        }

        DiscussionDataManager.deleteQuestion(questionId, currentUserId, new DiscussionDataManager.ActionCallback() {
            @Override
            public void onSuccess(String message) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Đã xóa câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                        // Reload discussions để cập nhật danh sách
                        loadDiscussions();
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Lỗi xóa câu hỏi: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public int getCurrentUserId() {
        return currentUserId;
    }
}
