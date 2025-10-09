package com.example.study_app.model;

public class Message {
    private String id;
    private String text;
    private String sender;
    private long timestamp;
    private boolean isFromUser;

    // Constructor cho chat message với isFromUser flag
    public Message(String text, boolean isFromUser) {
        this.text = text;
        this.isFromUser = isFromUser;
        this.timestamp = System.currentTimeMillis();
        this.sender = isFromUser ? "User" : "Bot";
    }

    // Constructor cũ để tương thích
    public Message(String text, String sender, long timestamp) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
        this.isFromUser = "User".equals(sender);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSender(String sender) {
        this.sender = sender;
        this.isFromUser = "User".equals(sender);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setFromUser(boolean fromUser) {
        this.isFromUser = fromUser;
    }
}