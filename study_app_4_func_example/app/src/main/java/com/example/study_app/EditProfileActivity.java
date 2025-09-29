package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.study_app.data.ApiClient;
import com.example.study_app.data.AuthAPI;
import com.example.study_app.model.AuthResponse;
import com.example.study_app.model.UpdateProfileRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword, editTextFullName;
    private Button buttonSave, buttonCancel;
    private AuthAPI authAPI;
    private int userId;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize API
        authAPI = ApiClient.getClient().create(AuthAPI.class);

        // Get user data from intent
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        currentUsername = intent.getStringExtra("username");
        String currentFullName = intent.getStringExtra("fullName");

        if (userId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullName = findViewById(R.id.editTextFullName);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Set current values
        editTextUsername.setText(currentUsername);
        if (currentFullName != null) {
            editTextFullName.setText(currentFullName);
        }

        // Button listeners
        buttonSave.setOnClickListener(v -> updateProfile());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void updateProfile() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        // Validation
        if (username.isEmpty()) {
            editTextUsername.setError("Tên đăng nhập không được để trống");
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Mật khẩu không được để trống");
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }

        // Disable button during API call
        buttonSave.setEnabled(false);
        buttonSave.setText("Đang cập nhật...");

        // Create request
        UpdateProfileRequest request = new UpdateProfileRequest(username, password, fullName);

        // Make API call
        Call<AuthResponse> call = authAPI.updateProfile(userId, request);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                buttonSave.setEnabled(true);
                buttonSave.setText("Lưu thay đổi");

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        Toast.makeText(EditProfileActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // Return updated data to MainActivity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updatedUsername", authResponse.getUser().getUsername());
                        resultIntent.putExtra("updatedFullName", authResponse.getUser().getFullName());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(EditProfileActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(EditProfileActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                buttonSave.setEnabled(true);
                buttonSave.setText("Lưu thay đổi");
                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
