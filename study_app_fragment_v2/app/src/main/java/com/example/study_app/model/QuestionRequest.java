package com.example.study_app.model;

public class QuestionRequest {
    private int user_id;
    private String content;

    public QuestionRequest(int user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    // Getters
    public int getUser_id() { return user_id; }
    public String getContent() { return content; }

    // Setters
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setContent(String content) { this.content = content; }
}
