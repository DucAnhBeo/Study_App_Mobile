package com.example.study_app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discussion {
    private String id;
    private String question;
    private String author;
    private Date createdDate;
    private List<Answer> answers;

    public Discussion(String id, String question, String author, Date createdDate) {
        this.id = id;
        this.question = question;
        this.author = author;
        this.createdDate = createdDate;
        this.answers = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getQuestion() { return question; }
    public String getAuthor() { return author; }
    public Date getCreatedDate() { return createdDate; }
    public List<Answer> getAnswers() { return answers; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setQuestion(String question) { this.question = question; }
    public void setAuthor(String author) { this.author = author; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    // Methods
    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public int getAnswerCount() {
        return answers.size();
    }
}
