package com.example.study_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.study_app.data.ApiClient;
import com.example.study_app.model.AuthResponse;
import com.example.study_app.model.GoogleSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    // UI Components
    private com.google.android.material.textfield.TextInputEditText editTextUsername, editTextPassword;
    private com.google.android.material.button.MaterialButton buttonLogin, buttonRegister, buttonGoogleLogin;
    private ProgressBar progressBar;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupGoogleSignIn();
        setupClickListeners();
    }



    private void initViews() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonGoogleLogin = findViewById(R.id.buttonGoogleLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        handleSignInResult(result.getData());
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        resetGoogleSignInUI();
                        Toast.makeText(this, "Đăng nhập Google bị hủy", Toast.LENGTH_SHORT).show();
                    } else {
                        resetGoogleSignInUI();
                        Toast.makeText(this, "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupClickListeners() {
        buttonLogin.setOnClickListener(v -> performLogin());
        buttonRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
        buttonGoogleLogin.setOnClickListener(v -> signInWithGoogle());
    }



    private void performLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoginLoading(true);
        com.example.study_app.model.LoginRequest request = new com.example.study_app.model.LoginRequest(username, password);

        ApiClient.getAuthAPI().login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                setLoginLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity(authResponse);
                    } else {
                        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMsg = response.code() == 401 ?
                            "Tên đăng nhập hoặc mật khẩu không đúng" : "Lỗi server";
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                setLoginLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void signInWithGoogle() {
        Log.d(TAG, "Starting Google Sign-In...");
        setGoogleLoginLoading(true);

        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        } catch (Exception e) {
            Log.e(TAG, "Error launching Google Sign-In", e);
            setGoogleLoginLoading(false);
            Toast.makeText(this, "Không thể khởi tạo Google Sign-In", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignInResult(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account != null) {
                Log.d(TAG, "Google Sign-In successful: " + account.getEmail());
                sendGoogleTokenToServer(account);
            } else {
                setGoogleLoginLoading(false);
                Toast.makeText(this, "Không nhận được thông tin tài khoản Google", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In failed with code: " + e.getStatusCode(), e);
            setGoogleLoginLoading(false);

            String errorMessage = getGoogleErrorMessage(e.getStatusCode());
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendGoogleTokenToServer(GoogleSignInAccount account) {
        String idToken = account.getIdToken();
        String email = account.getEmail();
        String name = account.getDisplayName();

        if (idToken == null || email == null) {
            setGoogleLoginLoading(false);
            Toast.makeText(this, "Thiếu thông tin xác thực từ Google", Toast.LENGTH_SHORT).show();
            return;
        }

        GoogleSignInRequest request = new GoogleSignInRequest(idToken, email, name);

        ApiClient.getAuthAPI().googleLogin(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                setGoogleLoginLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập Google thành công!", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity(authResponse);
                    } else {
                        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Lỗi server (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                setGoogleLoginLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private String getGoogleErrorMessage(int statusCode) {
        switch (statusCode) {
            case 7:
                return "Lỗi mạng. Kiểm tra kết nối internet.";
            case 10:
                return "Lỗi cấu hình Firebase! Cần thêm SHA-1 và enable Authentication.";
            case 12500:
                return "Đăng nhập bị hủy bởi người dùng.";
            case 12502:
                return "Đăng nhập thất bại. Thử lại sau.";
            default:
                return "Lỗi đăng nhập Google (Mã: " + statusCode + ")";
        }
    }

    private void navigateToMainActivity(AuthResponse authResponse) {
        Intent intent = new Intent(this, MainActivityNew.class);
        if (authResponse.getUser() != null) {
            intent.putExtra("userId", authResponse.getUser().getId());
            intent.putExtra("username", authResponse.getUser().getUsername());
            intent.putExtra("fullName", authResponse.getUser().getFullName());
        }
        startActivity(intent);
        finish();
    }

    private void setLoginLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        buttonLogin.setEnabled(!isLoading);
    }

    private void setGoogleLoginLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        buttonGoogleLogin.setEnabled(!isLoading);
        buttonGoogleLogin.setText(isLoading ? "Đang đăng nhập..." : "Đăng nhập bằng Google");
    }

    private void resetGoogleSignInUI() {
        setGoogleLoginLoading(false);
    }
}
