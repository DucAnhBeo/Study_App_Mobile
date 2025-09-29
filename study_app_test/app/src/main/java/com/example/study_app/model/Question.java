package com.example.study_app.model;

import java.util.List;

public class Question {
    private String id;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private String explanation;

    public Question() {
        // Constructor mặc định cần thiết cho serialization
    }

    public Question(String id, String questionText, List<String> options, int correctAnswerIndex, String explanation) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.explanation = explanation;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    // Method tiện ích
    public String getCorrectAnswer() {
        if (options != null && correctAnswerIndex >= 0 && correctAnswerIndex < options.size()) {
            return options.get(correctAnswerIndex);
        }
        return null;
    }

    public boolean isCorrectAnswer(int selectedIndex) {
        return selectedIndex == correctAnswerIndex;
    }
}
