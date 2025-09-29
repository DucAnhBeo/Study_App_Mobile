package com.example.study_app.model;

import java.util.Date;

public class Answer {
    private String id;
    private String content;
    private String author;
    private Date createdDate;

    public Answer(String id, String content, String author, Date createdDate) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
    }

    // Getters
    public String getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public Date getCreatedDate() { return createdDate; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
