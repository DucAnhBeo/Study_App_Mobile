package com.example.study_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.R;
import com.example.study_app.model.Lesson;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessons;
    private Context context;

    public LessonAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);

        holder.titleTextView.setText(lesson.getTitle());
        holder.descriptionTextView.setText(lesson.getDescription());
        holder.durationTextView.setText(lesson.getDuration() + " phút");

        holder.watchVideoButton.setOnClickListener(v -> {
            // Open video URL (in real app, use proper video player)
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lesson.getVideoUrl()));
            context.startActivity(intent);
        });

        holder.readLectureButton.setOnClickListener(v -> {
            // Show lecture content dialog or open new activity
            showLectureDialog(lesson);
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    private void showLectureDialog(Lesson lesson) {
        // For now, just show a simple alert
        androidx.appcompat.app.AlertDialog.Builder builder =
            new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(lesson.getTitle())
               .setMessage(lesson.getLectureContent())
               .setPositiveButton("Đóng", null)
               .show();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView durationTextView;
        Button watchVideoButton;
        Button readLectureButton;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewLessonTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewLessonDescription);
            durationTextView = itemView.findViewById(R.id.textViewDuration);
            watchVideoButton = itemView.findViewById(R.id.buttonWatchVideo);
            readLectureButton = itemView.findViewById(R.id.buttonReadLecture);
        }
    }
}
