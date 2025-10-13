package com.example.study_app.model;

public class AnswerRequest {
    private int question_id;
    private int user_id;
    private String content;

    public AnswerRequest(int question_id, int user_id, String content) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.content = content;
    }

    // Getters
    public int getQuestion_id() { return question_id; }
    public int getUser_id() { return user_id; }
    public String getContent() { return content; }

    // Setters
    public void setQuestion_id(int question_id) { this.question_id = question_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setContent(String content) { this.content = content; }
}
