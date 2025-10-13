package com.example.study_app.model;

public class GoogleSignInRequest {
    private String idToken;
    private String email;
    private String name;

    public GoogleSignInRequest(String idToken, String email, String name) {
        this.idToken = idToken;
        this.email = email;
        this.name = name;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
