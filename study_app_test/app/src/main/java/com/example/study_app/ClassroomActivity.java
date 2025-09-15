package com.example.study_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.adapter.LessonAdapter;
import com.example.study_app.data.LessonDataManager;
import com.example.study_app.model.Lesson;
import java.util.List;

public class ClassroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        // Get selected grade and textbook from intent
        String selectedGrade = getIntent().getStringExtra("grade");
        String selectedTextbook = getIntent().getStringExtra("textbook");

        // Initialize views
        TextView titleView = findViewById(R.id.textViewTitle);
        TextView subjectView = findViewById(R.id.textViewSubject);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLessons);
        TextView emptyStateView = findViewById(R.id.textViewEmptyState);

        // Display the selected grade and textbook
        if (selectedGrade != null && selectedTextbook != null) {
            String formattedTitle = getString(R.string.classroom_title) + "\n" +
                    getString(R.string.classroom_info, selectedGrade, selectedTextbook);
            titleView.setText(formattedTitle);
            subjectView.setText("Môn: Hóa học");

            // Get lessons data
            List<Lesson> lessons = LessonDataManager.getLessons(selectedGrade, selectedTextbook);

            if (lessons.isEmpty()) {
                // Show empty state
                recyclerView.setVisibility(View.GONE);
                emptyStateView.setVisibility(View.VISIBLE);
                emptyStateView.setText("Chưa có bài học nào cho " + selectedGrade + " - " + selectedTextbook);
            } else {
                // Setup RecyclerView with lessons
                recyclerView.setVisibility(View.VISIBLE);
                emptyStateView.setVisibility(View.GONE);

                LessonAdapter adapter = new LessonAdapter(this, lessons);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }
        } else {
            titleView.setText(R.string.classroom_title);
            subjectView.setText("Môn: Hóa học");
            recyclerView.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);
        }
    }
}
