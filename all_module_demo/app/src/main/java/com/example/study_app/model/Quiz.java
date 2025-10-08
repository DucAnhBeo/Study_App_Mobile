package com.example.study_app.model;

import java.util.List;

public class Quiz {
    private String id;
    private String title;
    private String description;
    private String grade;
    private String textbook;
    private List<Question> questions;
    private int timeLimit; // in minutes

    public Quiz(String id, String title, String description, String grade, String textbook, List<Question> questions, int timeLimit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.grade = grade;
        this.textbook = textbook;
        this.questions = questions;
        this.timeLimit = timeLimit;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGrade() { return grade; }
    public String getTextbook() { return textbook; }
    public List<Question> getQuestions() { return questions; }
    public int getTimeLimit() { return timeLimit; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setTextbook(String textbook) { this.textbook = textbook; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

    public int getQuestionCount() {
        return questions != null ? questions.size() : 0;
    }
}
