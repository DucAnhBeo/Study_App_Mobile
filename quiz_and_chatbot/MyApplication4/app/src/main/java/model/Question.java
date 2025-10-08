package model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    private List<String> allOptions;
    private int selectedAnswerIndex = -1;
    private int correctOptionIndex = -1;

    public Question(String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public List<String> getAllOptions() {
        return allOptions;
    }

    public void setAllOptions(List<String> allOptions) {
        this.allOptions = allOptions;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public int getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public void setSelectedAnswerIndex(int selectedAnswerIndex) {
        this.selectedAnswerIndex = selectedAnswerIndex;
    }
}