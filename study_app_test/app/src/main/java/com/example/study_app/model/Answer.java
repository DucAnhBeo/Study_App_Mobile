package com.example.study_app.model;

import java.util.Date;

public class Answer {
    private int id;
    private String content;
    private String author;
    private String created_at;

    public Answer(int id, String content, String author, String created_at) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.created_at = created_at;
    }

    // Constructor mặc định cho JSON parsing
    public Answer() {}

    // Getters
    public int getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getCreatedAt() { return created_at; }

    // Getter cho compatibility với code cũ
    public Date getCreatedDate() {
        // Có thể parse created_at string thành Date nếu cần
        return new Date();
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    public void setCreatedAt(String created_at) { this.created_at = created_at; }
}
