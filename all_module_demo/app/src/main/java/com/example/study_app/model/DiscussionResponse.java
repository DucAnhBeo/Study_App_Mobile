package com.example.study_app.model;

import java.util.List;

public class DiscussionResponse {
    private boolean success;
    private List<Discussion> questions;
    private String message;

    public DiscussionResponse() {}

    // Getters
    public boolean isSuccess() { return success; }
    public List<Discussion> getQuestions() { return questions; }
    public String getMessage() { return message; }

    // Setters
    public void setSuccess(boolean success) { this.success = success; }
    public void setQuestions(List<Discussion> questions) { this.questions = questions; }
    public void setMessage(String message) { this.message = message; }
}
