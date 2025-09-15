package com.example.study_app.model;

public class Lesson {
    private String title;
    private String description;
    private String videoUrl;
    private String lectureContent;
    private int duration; // in minutes

    public Lesson(String title, String description, String videoUrl, String lectureContent, int duration) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.lectureContent = lectureContent;
        this.duration = duration;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVideoUrl() { return videoUrl; }
    public String getLectureContent() { return lectureContent; }
    public int getDuration() { return duration; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public void setLectureContent(String lectureContent) { this.lectureContent = lectureContent; }
    public void setDuration(int duration) { this.duration = duration; }
}
