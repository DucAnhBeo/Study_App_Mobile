package com.example.study_app.data;

import com.example.study_app.model.Answer;
import com.example.study_app.model.AnswerRequest;
import com.example.study_app.model.AuthResponse;
import com.example.study_app.model.Discussion;
import com.example.study_app.model.DiscussionResponse;
import com.example.study_app.model.QuestionRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionDataManager {
    private static List<Discussion> discussions = new ArrayList<>();
    private static AuthAPI apiService = ApiClient.getAuthAPI();

    public interface DiscussionCallback {
        void onSuccess(List<Discussion> discussions);
        void onError(String error);
    }

    public interface ActionCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    // Lấy tất cả discussions từ API
    public static void getAllDiscussions(DiscussionCallback callback) {
        Call<DiscussionResponse> call = apiService.getDiscussions();
        call.enqueue(new Callback<DiscussionResponse>() {
            @Override
            public void onResponse(Call<DiscussionResponse> call, Response<DiscussionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DiscussionResponse discussionResponse = response.body();
                    if (discussionResponse.isSuccess()) {
                        discussions = discussionResponse.getQuestions();
                        callback.onSuccess(discussions);
                    } else {
                        callback.onError(discussionResponse.getMessage());
                    }
                } else {
                    callback.onError("Lỗi kết nối server");
                }
            }

            @Override
            public void onFailure(Call<DiscussionResponse> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    // Tạo câu hỏi mới
    public static void createQuestion(int userId, String content, ActionCallback callback) {
        QuestionRequest request = new QuestionRequest(userId, content);
        Call<AuthResponse> call = apiService.createQuestion(request);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        callback.onSuccess(authResponse.getMessage());
                    } else {
                        callback.onError(authResponse.getMessage());
                    }
                } else {
                    callback.onError("Lỗi kết nối server");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    // Tạo câu trả lời
    public static void createAnswer(int questionId, int userId, String content, ActionCallback callback) {
        AnswerRequest request = new AnswerRequest(questionId, userId, content);
        Call<AuthResponse> call = apiService.createAnswer(request);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        callback.onSuccess(authResponse.getMessage());
                    } else {
                        callback.onError(authResponse.getMessage());
                    }
                } else {
                    callback.onError("Lỗi kết nối server");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    // Xóa câu hỏi
    public static void deleteQuestion(int questionId, int userId, ActionCallback callback) {
        QuestionRequest request = new QuestionRequest(userId, ""); // Chỉ cần userId để xác thực
        Call<AuthResponse> call = apiService.deleteQuestion(questionId, request);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        callback.onSuccess(authResponse.getMessage());
                    } else {
                        callback.onError(authResponse.getMessage());
                    }
                } else {
                    callback.onError("Lỗi kết nối server");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    // Tìm discussion theo ID (local cache)
    public static Discussion findDiscussionById(int id) {
        for (Discussion discussion : discussions) {
            if (discussion.getId() == id) {
                return discussion;
            }
        }
        return null;
    }

    // Lấy discussions từ cache local (để tương thích với code cũ)
    public static List<Discussion> getAllDiscussions() {
        return discussions;
    }

    // Thêm discussion vào cache local (để tương thích với code cũ)
    public static void addDiscussion(Discussion discussion) {
        discussions.add(0, discussion);
    }

    // Thêm answer vào discussion (để tương thích với code cũ)
    public static void addAnswerToDiscussion(String discussionId, Answer answer) {
        try {
            int id = Integer.parseInt(discussionId);
            Discussion discussion = findDiscussionById(id);
            if (discussion != null) {
                discussion.addAnswer(answer);
            }
        } catch (NumberFormatException e) {
            // Handle error
        }
    }
}
