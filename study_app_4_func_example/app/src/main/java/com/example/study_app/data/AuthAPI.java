package com.example.study_app.data;

import com.example.study_app.model.AuthResponse;
import com.example.study_app.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("api/register")
    Call<AuthResponse> register(@Body LoginRequest request);
}
