package com.example.study_app;

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
import com.example.study_app.adapter.DiscussionAdapter;
import com.example.study_app.data.DiscussionDataManager;
import com.example.study_app.model.Discussion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiscussionAdapter adapter;
    private List<Discussion> discussions;
    private List<Discussion> filteredDiscussions;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private TextView textViewNoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewDiscussions);
        FloatingActionButton fabAddQuestion = findViewById(R.id.fabAddQuestion);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonClearSearch = findViewById(R.id.buttonClearSearch);
        textViewNoResults = findViewById(R.id.textViewNoResults);

        // Setup RecyclerView
        discussions = DiscussionDataManager.getAllDiscussions();
        filteredDiscussions = new ArrayList<>(discussions);
        adapter = new DiscussionAdapter(this, filteredDiscussions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup search functionality
        setupSearchFeature();

        // Setup FAB for adding new question
        fabAddQuestion.setOnClickListener(v -> showAddQuestionDialog());
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
            textViewNoResults.setText(getString(R.string.no_results_with_keyword, query));
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textViewNoResults.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }

    private void showAddQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.create_new_question);

        // Create EditText for question input
        final EditText input = new EditText(this);
        input.setHint(getString(R.string.search_hint));
        input.setMinLines(3);
        builder.setView(input);

        builder.setPositiveButton(R.string.post_question, (dialog, which) -> {
            String question = input.getText().toString().trim();
            if (!question.isEmpty()) {
                // Create new discussion
                String id = String.valueOf(System.currentTimeMillis());
                Discussion newDiscussion = new Discussion(id, question, getString(R.string.admin_username), new Date());

                // Add to data manager
                DiscussionDataManager.addDiscussion(newDiscussion);

                // Update both lists
                discussions.clear();
                discussions.addAll(DiscussionDataManager.getAllDiscussions());

                // Clear search and show all discussions
                editTextSearch.setText("");
                filterDiscussions("");

                recyclerView.scrollToPosition(0);

                Toast.makeText(this, R.string.question_posted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.enter_question_prompt, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
