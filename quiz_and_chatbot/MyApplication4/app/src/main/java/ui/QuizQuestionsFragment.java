package ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import model.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizQuestionsFragment extends Fragment {

    private ArrayList<Question> questionList;
    private int currentIndex = 0;
    private int score = 0;

    private TextView tvQuestionContent;
    private Button[] optionButtons = new Button[4];
    private Button prevButton;
    private Button nextButton;

    private static final String TAG = "QuizFragment";
    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String API_URL = "http://192.168.0.105:3000/questions";
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean listenersSetup = false;

    public QuizQuestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_questions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            tvQuestionContent = view.findViewById(R.id.tvQuestionContent);
            optionButtons[0] = view.findViewById(R.id.btnAnswer0);
            optionButtons[1] = view.findViewById(R.id.btnAnswer1);
            optionButtons[2] = view.findViewById(R.id.btnAnswer2);
            optionButtons[3] = view.findViewById(R.id.btnAnswer3);
            prevButton = view.findViewById(R.id.prevQues);
            nextButton = view.findViewById(R.id.nextQues);

            if (prevButton != null) prevButton.setEnabled(false);
            if (nextButton != null) nextButton.setEnabled(false);

            fetchQuestions(API_URL);
        } catch (Exception e) {
            Log.e(TAG, "Error in onViewCreated: " + e.getMessage());
        }
    }

    private void fetchQuestions(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Network error: " + e.getMessage());
                e.printStackTrace();
                showError("Network connection failed.");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful() || responseBody == null) {
                        Log.e(TAG, "HTTP Error: Code " + response.code());
                        showError("Server error: HTTP Code " + response.code());
                        return;
                    }

                    String jsonString = responseBody.string();
                    final ArrayList<Question> fetchedQuestions = parseQuestions(jsonString);

                    mainHandler.post(() -> {
                        if (isAdded() && getView() != null) {
                            questionList = fetchedQuestions;
                            if (questionList != null && !questionList.isEmpty()) {
                                if (!listenersSetup) {
                                    setupListeners();
                                    listenersSetup = true;
                                }
                                displayQuestion(currentIndex);
                                showToast("Data loaded successfully!");
                            } else {
                                showError("No questions loaded.");
                            }
                        }
                    });

                } catch (Exception e) {
                    Log.e(TAG, "Error processing response: " + e.getMessage());
                    e.printStackTrace();
                    showError("Data processing error.");
                }
            }
        });
    }

    private ArrayList<Question> parseQuestions(String jsonString) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String questionContent = jsonObject.getString("question");
                String correctAnswer = jsonObject.getString("correct_answer");

                JSONArray incorrectArray = jsonObject.getJSONArray("incorrect_answers");
                ArrayList<String> incorrectAnswers = new ArrayList<>();
                for (int j = 0; j < incorrectArray.length(); j++) {
                    incorrectAnswers.add(incorrectArray.getString(j));
                }

                Question q = new Question(questionContent, correctAnswer, incorrectAnswers);

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(correctAnswer);
                allAnswers.addAll(incorrectAnswers);

                String[] sortedOptionsArray = new String[4];

                for (String answer : allAnswers) {
                    if (answer != null && answer.length() > 0) {
                        char prefix = answer.toUpperCase().charAt(0);
                        int index = -1;
                        if (prefix >= 'A' && prefix <= 'D') {
                            index = prefix - 'A';
                        }

                        if (index >= 0 && index < 4) {
                            sortedOptionsArray[index] = answer;
                        } else {
                            Log.e(TAG, "Parsing error: Invalid answer format: " + answer);
                        }
                    }
                }

                ArrayList<String> allOptions = new ArrayList<>(Arrays.asList(sortedOptionsArray));
                q.setAllOptions(allOptions);

                // A luôn đúng (index 0)
                q.setCorrectOptionIndex(0);

                questions.add(q);
            }
        } catch (Exception e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return questions;
    }

    private void displayQuestion(int index) {
        try {
            if (questionList == null || index < 0 || index >= questionList.size()) {
                showError("No questions to display.");
                return;
            }

            Question currentQuestion = questionList.get(index);

            if (tvQuestionContent != null) {
                tvQuestionContent.setText(currentQuestion.getQuestion());
            }

            List<String> options = currentQuestion.getAllOptions();

            for (int i = 0; i < 4; i++) {
                if (optionButtons[i] != null && options != null && i < options.size()) {
                    Button btn = optionButtons[i];
                    String optionText = options.get(i);
                    btn.setText(optionText != null ? optionText : "");
                    btn.setEnabled(true);

                    try {
                        btn.setBackgroundResource(R.drawable.rounded_button);
                        btn.setBackgroundTintList(null);
                    } catch (Exception e) {
                        Log.e(TAG, "Error setting button background: " + e.getMessage());
                    }

                    if (currentQuestion.getSelectedAnswerIndex() == i) {
                        if (i == 0) { // A luôn đúng
                            setButtonColor(btn, R.color.light_green);
                        } else {
                            setButtonColor(btn, R.color.red);
                        }
                        disableAllButtons();
                    }
                }
            }

            if (prevButton != null) {
                prevButton.setEnabled(index > 0);
            }
            if (nextButton != null) {
                nextButton.setEnabled(index < questionList.size() - 1);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error displaying question: " + e.getMessage());
        }
    }

    private void setupListeners() {
        try {
            for (int i = 0; i < optionButtons.length; i++) {
                final int selectedIndex = i;
                if (optionButtons[i] != null) {
                    optionButtons[i].setOnClickListener(v -> {
                        handleAnswerSelection(selectedIndex);
                    });
                }
            }

            if (nextButton != null) {
                nextButton.setOnClickListener(v -> {
                    if (questionList != null && currentIndex < questionList.size() - 1) {
                        currentIndex++;
                        displayQuestion(currentIndex);
                    }
                });
            }

            if (prevButton != null) {
                prevButton.setOnClickListener(v -> {
                    if (currentIndex > 0) {
                        currentIndex--;
                        displayQuestion(currentIndex);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up listeners: " + e.getMessage());
        }
    }

    private void handleAnswerSelection(int selectedIndex) {
        try {
            if (questionList == null || currentIndex >= questionList.size()) {
                return;
            }

            Question currentQuestion = questionList.get(currentIndex);

            if (currentQuestion.getSelectedAnswerIndex() != -1) {
                return;
            }

            currentQuestion.setSelectedAnswerIndex(selectedIndex);

            // A luôn đúng (index 0)
            int correctIndex = 0;

            if (selectedIndex == correctIndex) {
                score++;
                showToast("Correct!");
                setButtonColor(optionButtons[selectedIndex], R.color.light_green);
            } else {
                showToast("Incorrect.");
                setButtonColor(optionButtons[selectedIndex], R.color.red);
                setButtonColor(optionButtons[correctIndex], R.color.light_green);
            }

            disableAllButtons();

            // Tự động nhảy sang câu tiếp theo sau 1 giây
            mainHandler.postDelayed(() -> {
                if (isAdded() && questionList != null) {
                    if (currentIndex < questionList.size() - 1) {
                        currentIndex++;
                        displayQuestion(currentIndex);
                    } else {
                        showToast("Quiz completed! Score: " + score + "/" + questionList.size());
                    }
                }
            }, 1000);
        } catch (Exception e) {
            Log.e(TAG, "Error handling answer selection: " + e.getMessage());
        }
    }

    private void setButtonColor(Button btn, int colorRes) {
        try {
            if (btn != null && getContext() != null) {
                btn.setBackgroundResource(R.drawable.rounded_button);
                btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), colorRes)
                ));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting button color: " + e.getMessage());
        }
    }

    private void disableAllButtons() {
        for (Button btn : optionButtons) {
            if (btn != null) {
                btn.setEnabled(false);
            }
        }
    }

    private void showError(String message) {
        mainHandler.post(() -> {
            if (isAdded() && getContext() != null) {
                if (tvQuestionContent != null) {
                    tvQuestionContent.setText(message);
                }
                disableAllButtons();
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showToast(String message) {
        if (isAdded() && getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainHandler.removeCallbacksAndMessages(null);
    }
}
