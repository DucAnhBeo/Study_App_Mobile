package com.example.study_app.data;

import com.example.study_app.model.AuthResponse;
import com.example.study_app.model.LoginRequest;
import com.example.study_app.model.UpdateProfileRequest;
import com.example.study_app.model.DiscussionResponse;
import com.example.study_app.model.QuestionRequest;
import com.example.study_app.model.AnswerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthAPI {
    // Auth endpoints
    @POST("api/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("api/register")
    Call<AuthResponse> register(@Body LoginRequest request);

    @PUT("api/profile/{userId}")
    Call<AuthResponse> updateProfile(@Path("userId") int userId, @Body UpdateProfileRequest request);

    // Discussion endpoints
    @GET("api/discussion/questions")
    Call<DiscussionResponse> getDiscussions();

    @POST("api/discussion/questions")
    Call<AuthResponse> createQuestion(@Body QuestionRequest request);

    @POST("api/discussion/answers")
    Call<AuthResponse> createAnswer(@Body AnswerRequest request);

    @DELETE("api/discussion/questions/{questionId}")
    Call<AuthResponse> deleteQuestion(@Path("questionId") int questionId, @Body QuestionRequest userIdRequest);
}
