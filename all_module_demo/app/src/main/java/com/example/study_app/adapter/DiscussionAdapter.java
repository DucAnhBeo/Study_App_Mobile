package com.example.study_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.R;
import com.example.study_app.data.DiscussionDataManager;
import com.example.study_app.model.Answer;
import com.example.study_app.model.Discussion;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder> {
    private Context context;
    private List<Discussion> discussions;
    private SimpleDateFormat dateFormat;

    public DiscussionAdapter(Context context, List<Discussion> discussions) {
        this.context = context;
        this.discussions = discussions;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discussion, parent, false);
        return new DiscussionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position) {
        Discussion discussion = discussions.get(position);

        holder.questionTextView.setText(discussion.getQuestion());
        holder.authorTextView.setText("Hỏi bởi: " + discussion.getAuthor());
        holder.dateTextView.setText(dateFormat.format(discussion.getCreatedDate()));
        holder.answerCountTextView.setText(discussion.getAnswerCount() + " trả lời");

        // Clear previous answers
        holder.answersLayout.removeAllViews();

        // Add answers
        for (Answer answer : discussion.getAnswers()) {
            View answerView = createAnswerView(answer);
            holder.answersLayout.addView(answerView);
        }

        // Handle add answer button
        holder.addAnswerButton.setOnClickListener(v -> showAddAnswerDialog(discussion, holder));
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }

    private View createAnswerView(Answer answer) {
        View answerView = LayoutInflater.from(context).inflate(R.layout.item_answer, null);

        TextView contentTextView = answerView.findViewById(R.id.textViewAnswerContent);
        TextView authorTextView = answerView.findViewById(R.id.textViewAnswerAuthor);
        TextView dateTextView = answerView.findViewById(R.id.textViewAnswerDate);

        contentTextView.setText(answer.getContent());
        authorTextView.setText(answer.getAuthor());
        dateTextView.setText(dateFormat.format(answer.getCreatedDate()));

        return answerView;
    }

    private void showAddAnswerDialog(Discussion discussion, DiscussionViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thêm câu trả lời");

        final EditText input = new EditText(context);
        input.setHint("Nhập câu trả lời của bạn...");
        input.setMinLines(2);
        builder.setView(input);

        builder.setPositiveButton("Gửi trả lời", (dialog, which) -> {
            String content = input.getText().toString().trim();
            if (!content.isEmpty()) {
                String answerId = discussion.getId() + "-" + System.currentTimeMillis();
                Answer newAnswer = new Answer(answerId, content, "admin", new Date());

                DiscussionDataManager.addAnswerToDiscussion(discussion.getId(), newAnswer);

                // Add answer view to UI
                View answerView = createAnswerView(newAnswer);
                holder.answersLayout.addView(answerView);

                // Update answer count
                holder.answerCountTextView.setText(discussion.getAnswerCount() + " trả lời");

                Toast.makeText(context, "Câu trả lời đã được thêm!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Vui lòng nhập câu trả lời!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    static class DiscussionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView authorTextView;
        TextView dateTextView;
        TextView answerCountTextView;
        LinearLayout answersLayout;
        Button addAnswerButton;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.textViewQuestion);
            authorTextView = itemView.findViewById(R.id.textViewAuthor);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            answerCountTextView = itemView.findViewById(R.id.textViewAnswerCount);
            answersLayout = itemView.findViewById(R.id.layoutAnswers);
            addAnswerButton = itemView.findViewById(R.id.buttonAddAnswer);
        }
    }
}
