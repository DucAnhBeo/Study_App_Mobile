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
import com.example.study_app.fragments.DiscussionFragment;
import com.example.study_app.model.Answer;
import com.example.study_app.model.Discussion;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder> {
    private Context context;
    private List<Discussion> discussions;
    private SimpleDateFormat dateFormat;
    private OnDiscussionActionListener actionListener;

    // Interface để Fragment có thể handle các action
    public interface OnDiscussionActionListener {
        void onCreateAnswer(int discussionId, String content);
        void onDeleteQuestion(int questionId);
        String getCurrentUsername();
        int getCurrentUserId();
    }

    public DiscussionAdapter(Context context, List<Discussion> discussions) {
        this.context = context;
        this.discussions = discussions;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    public void setActionListener(OnDiscussionActionListener listener) {
        this.actionListener = listener;
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

        holder.questionTextView.setText(discussion.getContent());
        holder.authorTextView.setText("Hỏi bởi: " + discussion.getAuthor());
        holder.dateTextView.setText(formatDate(discussion.getCreatedAt()));

        // Hiển thị số câu trả lời
        int answerCount = discussion.getAnswers() != null ? discussion.getAnswers().size() : 0;
        holder.answerCountTextView.setText(answerCount + " trả lời");

        // Clear previous answers và setup lại
        holder.answersLayout.removeAllViews();

        // Hiển thị câu trả lời (chỉ hiển thị tối đa 3 câu đầu tiên)
        if (discussion.getAnswers() != null && !discussion.getAnswers().isEmpty()) {
            int maxAnswersToShow = Math.min(3, discussion.getAnswers().size());
            for (int i = 0; i < maxAnswersToShow; i++) {
                Answer answer = discussion.getAnswers().get(i);
                View answerView = createAnswerView(answer);
                if (answerView != null) {
                    holder.answersLayout.addView(answerView);
                }
            }

            // Nếu có nhiều hơn 3 câu trả lời, hiển thị nút "Xem thêm"
            if (discussion.getAnswers().size() > 3) {
                TextView viewMoreText = new TextView(context);
                viewMoreText.setText("👁️ Xem thêm " + (discussion.getAnswers().size() - 3) + " câu trả lời");
                viewMoreText.setTextColor(context.getResources().getColor(R.color.primary_color, null));
                viewMoreText.setTextSize(13);
                viewMoreText.setPadding(16, 8, 16, 8);
                viewMoreText.setOnClickListener(v -> showAllAnswersDialog(discussion));
                holder.answersLayout.addView(viewMoreText);
            }
        }

        // Handle add answer button
        holder.addAnswerButton.setOnClickListener(v -> showAddAnswerDialog(discussion));

        // Show delete button only for current user's questions
        try {
            if (actionListener != null) {
                String currentUsername = actionListener.getCurrentUsername();

                if (currentUsername != null && currentUsername.equals(discussion.getAuthor())) {
                    holder.deleteButton.setVisibility(View.VISIBLE);
                    holder.deleteButton.setOnClickListener(v -> showDeleteConfirmDialog(discussion.getId()));
                } else {
                    holder.deleteButton.setVisibility(View.GONE);
                }
            } else {
                holder.deleteButton.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return discussions != null ? discussions.size() : 0;
    }

    private String formatDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            try {
                // Trả về date string trực tiếp hoặc có thể parse và format lại
                return dateString;
            } catch (Exception e) {
                return dateString;
            }
        }
        return "";
    }

    private View createAnswerView(Answer answer) {
        try {
            View answerView = LayoutInflater.from(context).inflate(R.layout.item_answer, null);

            TextView contentTextView = answerView.findViewById(R.id.textViewAnswerContent);
            TextView authorTextView = answerView.findViewById(R.id.textViewAnswerAuthor);
            TextView dateTextView = answerView.findViewById(R.id.textViewAnswerDate);

            if (contentTextView != null) {
                contentTextView.setText(answer.getContent() != null ? answer.getContent() : "");
            }
            if (authorTextView != null) {
                authorTextView.setText(answer.getAuthor() != null ? answer.getAuthor() : "");
            }
            if (dateTextView != null) {
                dateTextView.setText(formatDate(answer.getCreatedAt()));
            }

            return answerView;
        } catch (Exception e) {
            // Nếu có lỗi khi tạo view, trả về null
            return null;
        }
    }

    private void showAddAnswerDialog(Discussion discussion) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thêm câu trả lời");

            final EditText input = new EditText(context);
            input.setHint("Nhập câu trả lời của bạn...");
            input.setMinLines(2);
            builder.setView(input);

            builder.setPositiveButton("Gửi trả lời", (dialog, which) -> {
                String content = input.getText().toString().trim();
                if (!content.isEmpty()) {
                    // Sử dụng actionListener thay vì cast context
                    if (actionListener != null) {
                        actionListener.onCreateAnswer(discussion.getId(), content);
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập câu trả lời!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", null);
            builder.show();
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi mở dialog", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmDialog(int questionId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa câu hỏi này không?");

            builder.setPositiveButton("Xóa", (dialog, which) -> {
                // Sử dụng actionListener thay vì cast context
                if (actionListener != null) {
                    actionListener.onDeleteQuestion(questionId);
                }
            });

            builder.setNegativeButton("Hủy", null);
            builder.show();
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi mở dialog xóa", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllAnswersDialog(Discussion discussion) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Tất cả câu trả lời");

            // Tạo layout cho dialog
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Thêm tất cả các câu trả lời vào layout
            if (discussion.getAnswers() != null) {
                for (Answer answer : discussion.getAnswers()) {
                    View answerView = createAnswerView(answer);
                    if (answerView != null) {
                        layout.addView(answerView);
                    }
                }
            }

            builder.setView(layout);

            builder.setPositiveButton("Đóng", null);
            builder.show();
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi mở dialog xem tất cả câu trả lời", Toast.LENGTH_SHORT).show();
        }
    }

    static class DiscussionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView authorTextView;
        TextView dateTextView;
        TextView answerCountTextView;
        LinearLayout answersLayout;
        Button addAnswerButton;
        Button deleteButton;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.textViewQuestion);
            authorTextView = itemView.findViewById(R.id.textViewAuthor);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            answerCountTextView = itemView.findViewById(R.id.textViewAnswerCount);
            answersLayout = itemView.findViewById(R.id.layoutAnswers);
            addAnswerButton = itemView.findViewById(R.id.buttonAddAnswer);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
