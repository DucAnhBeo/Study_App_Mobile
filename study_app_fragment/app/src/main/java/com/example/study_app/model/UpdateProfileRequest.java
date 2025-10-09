package com.example.study_app.model;

public class UpdateProfileRequest {
    private String username;
    private String password;
    private String full_name;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.full_name = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
