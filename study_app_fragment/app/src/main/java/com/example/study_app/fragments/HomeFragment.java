package com.example.study_app.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.study_app.R;
import com.example.study_app.ReaderActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private String selectedGrade = "";
    private String selectedTextbook = "";
    private CardView selectedBookLayout;
    private TextView textViewSelectedInfo;
    private Button currentGradeButton = null;
    private Button currentTextbookButton = null;
    private ImageView imageViewBook;
    private String selectedTitle = null;
    private String selectedJsonAssetPath = null;
    private String selectedPdfUrl = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        selectedBookLayout = view.findViewById(R.id.selectedBookLayout);
        textViewSelectedInfo = view.findViewById(R.id.textViewSelectedInfo);
        imageViewBook = view.findViewById(R.id.imageViewBook);

        imageViewBook.setOnClickListener(v -> openReaderIfReady());

        // Grade selection buttons
        Button buttonGrade6 = view.findViewById(R.id.buttonGrade6);
        Button buttonGrade7 = view.findViewById(R.id.buttonGrade7);
        Button buttonGrade8 = view.findViewById(R.id.buttonGrade8);
        Button buttonGrade9 = view.findViewById(R.id.buttonGrade9);

        buttonGrade6.setOnClickListener(v -> selectGrade("Lớp 6"));
        buttonGrade7.setOnClickListener(v -> selectGrade("Lớp 7"));
        buttonGrade8.setOnClickListener(v -> selectGrade("Lớp 8"));
        buttonGrade9.setOnClickListener(v -> selectGrade("Lớp 9"));

        // Textbook selection buttons
        Button buttonCanhDieu = view.findViewById(R.id.buttonCanhDieu);
        Button buttonChanTroi = view.findViewById(R.id.buttonChanTroi);
        Button buttonKetNoi = view.findViewById(R.id.buttonKetNoi);

        buttonCanhDieu.setOnClickListener(v -> selectTextbook("Cánh diều"));
        buttonChanTroi.setOnClickListener(v -> selectTextbook("Chân trời sáng tạo"));
        buttonKetNoi.setOnClickListener(v -> selectTextbook("Kết nối tri thức"));

        return view;
    }

    private void selectGrade(String grade) {
        resetAllGradeButtons();
        selectedGrade = grade;

        Button clickedButton = getCurrentGradeButton(grade);
        if (clickedButton != null) {
            clickedButton.setSelected(true);
            currentGradeButton = clickedButton;
        }
        updateBookDisplay();
    }

    private void selectTextbook(String textbook) {
        resetAllTextbookButtons();
        selectedTextbook = textbook;

        Button clickedButton = getCurrentTextbookButton(textbook);
        if (clickedButton != null) {
            clickedButton.setSelected(true);
            currentTextbookButton = clickedButton;
        }

        updateBookDisplay();
    }

    private void openReaderIfReady() {
        if (selectedTitle != null && selectedPdfUrl != null) {
            Intent intent = new Intent(getActivity(), ReaderActivity.class);
            intent.putExtra("title", selectedTitle);
            intent.putExtra("pdf_url", selectedPdfUrl);
            intent.putExtra("jsonAssetPath", selectedJsonAssetPath);
            startActivity(intent);
        }
    }

    private void updateBookDisplay() {
        if (!selectedGrade.isEmpty() && !selectedTextbook.isEmpty()) {
            selectedBookLayout.setVisibility(View.VISIBLE);
            textViewSelectedInfo.setText(selectedGrade + "\n" + selectedTextbook);

            loadCoverImage(selectedGrade, selectedTextbook);

            imageViewBook.setContentDescription(selectedGrade + " - " + selectedTextbook);
            selectedTitle = selectedGrade + " - " + selectedTextbook;
            selectedJsonAssetPath = buildTocAssetPath(selectedGrade, selectedTextbook);

            String rawPdf = loadPdfUrlFromJson(selectedJsonAssetPath);
            selectedPdfUrl = rawPdf;
        } else {
            selectedBookLayout.setVisibility(View.GONE);
            selectedTitle = null;
            selectedJsonAssetPath = null;
            selectedPdfUrl = null;
        }
    }

    private void loadCoverImage(String grade, String textbook) {
        String coverAssetPath = buildCoverAssetPath(grade, textbook);
        try (InputStream is = getActivity().getAssets().open(coverAssetPath)) {
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp != null) {
                imageViewBook.setImageBitmap(bmp);
            } else {
                imageViewBook.setImageResource(R.drawable.book_image);
            }
        } catch (IOException e) {
            imageViewBook.setImageResource(R.drawable.book_image);
        }
    }

    private String mapSeriesCode(String textbook) {
        switch (textbook) {
            case "Cánh diều": return "CD";
            case "Chân trời sáng tạo": return "CTST";
            case "Kết nối tri thức": return "KNTT";
            default: return textbook;
        }
    }

    private String extractGradeNumber(String grade) {
        return grade.replace("Lớp", "").trim();
    }

    private String buildCoverAssetPath(String grade, String textbook) {
        String series = mapSeriesCode(textbook);
        String g = extractGradeNumber(grade);
        String fileName = series + g + ".png";
        return "cover_sgk/" + series + "/" + fileName;
    }

    private String buildTocAssetPath(String grade, String textbook) {
        String series = mapSeriesCode(textbook);
        String g = extractGradeNumber(grade);
        String fileName = series + g + ".json";
        return "table_of_content/" + series + "/" + fileName;
    }

    private String loadPdfUrlFromJson(String assetPath) {
        try (InputStream is = getActivity().getAssets().open(assetPath)) {
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            String json = new BufferedReader(reader).lines().collect(Collectors.joining());
            return new JSONObject(json).getString("pdf_url");
        } catch (Exception e) {
            return null;
        }
    }

    private Button getCurrentGradeButton(String grade) {
        if (getView() == null) return null;
        switch (grade) {
            case "Lớp 6":
                return getView().findViewById(R.id.buttonGrade6);
            case "Lớp 7":
                return getView().findViewById(R.id.buttonGrade7);
            case "Lớp 8":
                return getView().findViewById(R.id.buttonGrade8);
            case "Lớp 9":
                return getView().findViewById(R.id.buttonGrade9);
            default:
                return null;
        }
    }

    private Button getCurrentTextbookButton(String textbook) {
        if (getView() == null) return null;
        switch (textbook) {
            case "Cánh diều":
                return getView().findViewById(R.id.buttonCanhDieu);
            case "Chân trời sáng tạo":
                return getView().findViewById(R.id.buttonChanTroi);
            case "Kết nối tri thức":
                return getView().findViewById(R.id.buttonKetNoi);
            default:
                return null;
        }
    }

    private void resetAllGradeButtons() {
        if (getView() == null) return;

        Button buttonGrade6 = getView().findViewById(R.id.buttonGrade6);
        Button buttonGrade7 = getView().findViewById(R.id.buttonGrade7);
        Button buttonGrade8 = getView().findViewById(R.id.buttonGrade8);
        Button buttonGrade9 = getView().findViewById(R.id.buttonGrade9);

        // Chỉ cần set selected = false, color selector sẽ tự động xử lý màu
        buttonGrade6.setSelected(false);
        buttonGrade7.setSelected(false);
        buttonGrade8.setSelected(false);
        buttonGrade9.setSelected(false);
    }

    private void resetAllTextbookButtons() {
        if (getView() == null) return;

        Button buttonCanhDieu = getView().findViewById(R.id.buttonCanhDieu);
        Button buttonChanTroi = getView().findViewById(R.id.buttonChanTroi);
        Button buttonKetNoi = getView().findViewById(R.id.buttonKetNoi);

        // Chỉ cần set selected = false, color selector sẽ tự động xử lý màu
        buttonCanhDieu.setSelected(false);
        buttonChanTroi.setSelected(false);
        buttonKetNoi.setSelected(false);
    }
}
