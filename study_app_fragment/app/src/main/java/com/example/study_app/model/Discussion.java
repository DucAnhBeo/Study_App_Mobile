package com.example.study_app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discussion {
    private int id;
    private String content;
    private String author;
    private String created_at;
    private List<Answer> answers;

    public Discussion(int id, String content, String author, String created_at) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.created_at = created_at;
        this.answers = new ArrayList<>();
    }

    // Constructor mặc định cho JSON parsing
    public Discussion() {
        this.answers = new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getCreatedAt() { return created_at; }
    public List<Answer> getAnswers() { return answers; }

    // Getters cho compatibility với code cũ
    public String getQuestion() { return content; }
    public Date getCreatedDate() {
        // Có thể parse created_at string thành Date nếu cần
        return new Date();
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    public void setCreatedAt(String created_at) { this.created_at = created_at; }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    // Methods
    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public int getAnswerCount() {
        return answers.size();
    }
}
