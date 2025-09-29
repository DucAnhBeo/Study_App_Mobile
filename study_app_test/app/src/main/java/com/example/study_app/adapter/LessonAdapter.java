package com.example.study_app.adapter;

/**
 * LessonAdapter - Adapter cho RecyclerView hiển thị danh sách Lesson
 *
 * TRẠNG THÁI: ĐANG PHÁT TRIỂN - CHƯA HOÀN THÀNH
 *
 * Mục đích: Hiển thị danh sách bài học trong RecyclerView với giao diện thân thiện
 *
 * Chức năng dự kiến trong tương lai:
 * - Hiển thị thông tin bài học: tiêu đề, mô tả, thời lượng
 * - Hiển thị trạng thái học tập (chưa học/đang học/hoàn thành)
 * - Progress bar cho tiến độ học của từng bài
 * - Thumbnail cho video bài giảng
 * - Icon cho các loại media (video, audio, document)
 * - Button xem video, đọc bài, tải tài liệu
 * - Hiển thị độ khó với màu sắc khác nhau
 * - Animation khi click vào item
 * - Mark as favorite functionality
 * - Offline indicator cho bài đã tải xuống
 *
 * Kiến trúc:
 * - Kế thừa RecyclerView.Adapter
 * - LessonViewHolder để hold views của từng item
 * - OnLessonClickListener interface để handle events
 * - Layout: item_lesson.xml
 * - Support multiple view types (video lesson, text lesson, mixed)
 *
 * Tích hợp:
 * - Nhận List<Lesson> từ LessonDataManager
 * - Callback tới LessonActivity khi user click
 * - Chuyển tới LessonDetailActivity hoặc VideoPlayerActivity
 * - Tích hợp với progress tracking system
 *
 * Hiện tại: Code được comment để tránh lỗi compile
 */

// TODO: Phát triển chức năng LessonAdapter trong tương lai
// Adapter này sẽ hiển thị danh sách bài học trong RecyclerView
// Hiện tại đang được comment để tránh lỗi compile

/*
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.study_app.R;
import com.example.study_app.model.Lesson;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * LessonAdapter Implementation - Sẽ được uncomment khi phát triển
 */
/*
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    // ========== CONSTANTS ==========
    private static final int VIEW_TYPE_VIDEO_LESSON = 1;    // Bài học có video
    private static final int VIEW_TYPE_TEXT_LESSON = 2;     // Bài học chỉ có text
    private static final int VIEW_TYPE_MIXED_LESSON = 3;    // Bài học có cả video và text

    // ========== THUỘC TÍNH ==========
    private Context context;                           // Context của Activity
    private List<Lesson> lessons;                     // Danh sách bài học
    private OnLessonClickListener onLessonClickListener; // Listener cho events
    private String currentUserId;                     // ID user hiện tại để track progress

    // ========== INTERFACE ==========
    /**
     * Interface để handle click events từ lesson items
     */
    /*
    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);              // Click vào bài học
        void onVideoClick(Lesson lesson);               // Click xem video
        void onReadClick(Lesson lesson);                // Click đọc bài
        void onDownloadClick(Lesson lesson);            // Click tải về
        void onFavoriteClick(Lesson lesson);            // Click yêu thích
        void onLessonLongClick(Lesson lesson);          // Long click (menu options)
    }

    // ========== CONSTRUCTOR ==========
    /**
     * Constructor cho LessonAdapter
     */
    /*
    public LessonAdapter(Context context, List<Lesson> lessons, OnLessonClickListener listener, String userId) {
        this.context = context;
        this.lessons = lessons;
        this.onLessonClickListener = listener;
        this.currentUserId = userId;
    }

    // ========== RECYCLERVIEW METHODS ==========

    @Override
    public int getItemViewType(int position) {
        Lesson lesson = lessons.get(position);

        if (lesson.hasVideo() && lesson.hasContent()) {
            return VIEW_TYPE_MIXED_LESSON;
        } else if (lesson.hasVideo()) {
            return VIEW_TYPE_VIDEO_LESSON;
        } else {
            return VIEW_TYPE_TEXT_LESSON;
        }
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {
            case VIEW_TYPE_VIDEO_LESSON:
                layoutId = R.layout.item_lesson_video;
                break;
            case VIEW_TYPE_MIXED_LESSON:
                layoutId = R.layout.item_lesson_mixed;
                break;
            default:
                layoutId = R.layout.item_lesson_text;
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new LessonViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);

        // Set basic information
        holder.titleTextView.setText(lesson.getTitle());
        holder.descriptionTextView.setText(lesson.getDescription());
        holder.durationTextView.setText(lesson.getDuration() + " phút");
        holder.chapterTextView.setText(lesson.getChapter());
        holder.lessonNumberTextView.setText("Bài " + lesson.getLessonNumber());

        // Set difficulty with color
        setDifficultyColor(holder.difficultyTextView, lesson.getDifficulty());

        // Set progress for current user
        setLessonProgress(holder, lesson);

        // Load thumbnail for video lessons
        if (lesson.hasVideo() && holder.thumbnailImageView != null) {
            loadVideoThumbnail(holder.thumbnailImageView, lesson.getVideoUrl());
        }

        // Set media indicators
        setMediaIndicators(holder, lesson);

        // Set click listeners
        setupClickListeners(holder, lesson);
    }

    @Override
    public int getItemCount() {
        return lessons != null ? lessons.size() : 0;
    }

    // ========== HELPER METHODS ==========

    /**
     * Set màu sắc cho TextView độ khó
     */
    /*
    private void setDifficultyColor(TextView textView, String difficulty) {
        textView.setText(difficulty);

        switch (difficulty.toLowerCase()) {
            case "cơ bản":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                break;
            case "nâng cao":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
                break;
            case "chuyên sâu":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                break;
            default:
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
    }

    /**
     * Set tiến độ học tập cho bài học
     */
    /*
    private void setLessonProgress(LessonViewHolder holder, Lesson lesson) {
        // Get progress from database or SharedPreferences
        int progress = getLessonProgress(lesson.getId(), currentUserId);

        if (holder.progressBar != null) {
            holder.progressBar.setProgress(progress);
        }

        if (holder.statusTextView != null) {
            if (progress == 0) {
                holder.statusTextView.setText("Chưa học");
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
            } else if (progress == 100) {
                holder.statusTextView.setText("Hoàn thành");
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            } else {
                holder.statusTextView.setText("Đang học (" + progress + "%)");
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            }
        }
    }

    /**
     * Load thumbnail cho video lesson
     */
    /*
    private void loadVideoThumbnail(ImageView imageView, String videoUrl) {
        if (videoUrl.contains("youtube") || videoUrl.contains("youtu.be")) {
            String videoId = extractYouTubeVideoId(videoUrl);
            String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";

            Glide.with(context)
                 .load(thumbnailUrl)
                 .placeholder(R.drawable.placeholder_video)
                 .error(R.drawable.placeholder_video)
                 .into(imageView);
        } else {
            // For other video providers or local videos
            imageView.setImageResource(R.drawable.placeholder_video);
        }
    }

    /**
     * Set các indicator cho media types
     */
    /*
    private void setMediaIndicators(LessonViewHolder holder, Lesson lesson) {
        // Video indicator
        if (holder.videoIndicator != null) {
            holder.videoIndicator.setVisibility(lesson.hasVideo() ? View.VISIBLE : View.GONE);
        }

        // Audio indicator
        if (holder.audioIndicator != null) {
            holder.audioIndicator.setVisibility(lesson.hasAudio() ? View.VISIBLE : View.GONE);
        }

        // Document indicator
        if (holder.documentIndicator != null) {
            holder.documentIndicator.setVisibility(lesson.hasDocuments() ? View.VISIBLE : View.GONE);
        }

        // Offline indicator
        if (holder.offlineIndicator != null) {
            boolean isDownloaded = isLessonDownloaded(lesson.getId());
            holder.offlineIndicator.setVisibility(isDownloaded ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Setup click listeners cho các button
     */
    /*
    private void setupClickListeners(LessonViewHolder holder, Lesson lesson) {
        // Main item click
        holder.itemView.setOnClickListener(v -> {
            if (onLessonClickListener != null) {
                onLessonClickListener.onLessonClick(lesson);
            }
        });

        // Long click for menu
        holder.itemView.setOnLongClickListener(v -> {
            if (onLessonClickListener != null) {
                onLessonClickListener.onLessonLongClick(lesson);
            }
            return true;
        });

        // Watch video button
        if (holder.watchVideoButton != null) {
            holder.watchVideoButton.setOnClickListener(v -> {
                if (onLessonClickListener != null) {
                    onLessonClickListener.onVideoClick(lesson);
                }
            });
        }

        // Read lesson button
        if (holder.readLessonButton != null) {
            holder.readLessonButton.setOnClickListener(v -> {
                if (onLessonClickListener != null) {
                    onLessonClickListener.onReadClick(lesson);
                }
            });
        }

        // Download button
        if (holder.downloadButton != null) {
            holder.downloadButton.setOnClickListener(v -> {
                if (onLessonClickListener != null) {
                    onLessonClickListener.onDownloadClick(lesson);
                }
            });
        }

        // Favorite button
        if (holder.favoriteButton != null) {
            holder.favoriteButton.setOnClickListener(v -> {
                if (onLessonClickListener != null) {
                    onLessonClickListener.onFavoriteClick(lesson);
                }
            });
        }
    }

    // ========== UTILITY METHODS ==========

    private String extractYouTubeVideoId(String url) {
        // Extract video ID from YouTube URL
        return "dQw4w9WgXcQ"; // Placeholder
    }

    private int getLessonProgress(int lessonId, String userId) {
        // Get from database or SharedPreferences
        return 0; // Placeholder
    }

    private boolean isLessonDownloaded(int lessonId) {
        // Check if lesson is downloaded for offline viewing
        return false; // Placeholder
    }

    /**
     * Cập nhật danh sách lesson mới
     */
    /*
    public void updateLessons(List<Lesson> newLessons) {
        this.lessons = newLessons;
        notifyDataSetChanged();
    }

    // ========== VIEWHOLDER CLASS ==========

    /**
     * ViewHolder cho từng lesson item
     */
    /*
    static class LessonViewHolder extends RecyclerView.ViewHolder {
        // Text views
        TextView titleTextView;              // Tiêu đề bài học
        TextView descriptionTextView;        // Mô tả
        TextView durationTextView;           // Thời lượng
        TextView chapterTextView;            // Chương
        TextView lessonNumberTextView;       // Số bài
        TextView difficultyTextView;         // Độ khó
        TextView statusTextView;             // Trạng thái học tập

        // Media views
        ImageView thumbnailImageView;        // Thumbnail video
        ProgressBar progressBar;             // Progress bar

        // Indicator views
        ImageView videoIndicator;            // Icon video
        ImageView audioIndicator;            // Icon audio
        ImageView documentIndicator;         // Icon document
        ImageView offlineIndicator;          // Icon offline

        // Action buttons
        Button watchVideoButton;             // Xem video
        Button readLessonButton;             // Đọc bài
        Button downloadButton;               // Tải về
        ImageView favoriteButton;            // Yêu thích

        public LessonViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            // Initialize common views
            titleTextView = itemView.findViewById(R.id.textViewLessonTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewLessonDescription);
            durationTextView = itemView.findViewById(R.id.textViewDuration);
            chapterTextView = itemView.findViewById(R.id.textViewChapter);
            lessonNumberTextView = itemView.findViewById(R.id.textViewLessonNumber);
            difficultyTextView = itemView.findViewById(R.id.textViewDifficulty);
            statusTextView = itemView.findViewById(R.id.textViewStatus);
            progressBar = itemView.findViewById(R.id.progressBarLesson);

            // Initialize indicators
            videoIndicator = itemView.findViewById(R.id.imageViewVideoIndicator);
            audioIndicator = itemView.findViewById(R.id.imageViewAudioIndicator);
            documentIndicator = itemView.findViewById(R.id.imageViewDocumentIndicator);
            offlineIndicator = itemView.findViewById(R.id.imageViewOfflineIndicator);

            // Initialize buttons
            favoriteButton = itemView.findViewById(R.id.imageViewFavorite);

            // Initialize view-type specific views
            switch (viewType) {
                case VIEW_TYPE_VIDEO_LESSON:
                case VIEW_TYPE_MIXED_LESSON:
                    thumbnailImageView = itemView.findViewById(R.id.imageViewThumbnail);
                    watchVideoButton = itemView.findViewById(R.id.buttonWatchVideo);
                    break;
            }

            if (viewType == VIEW_TYPE_TEXT_LESSON || viewType == VIEW_TYPE_MIXED_LESSON) {
                readLessonButton = itemView.findViewById(R.id.buttonReadLesson);
            }

            downloadButton = itemView.findViewById(R.id.buttonDownload);
        }
    }
}
*/
