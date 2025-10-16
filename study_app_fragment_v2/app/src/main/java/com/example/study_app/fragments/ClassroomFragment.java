package com.example.study_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.study_app.R;

public class ClassroomFragment extends Fragment {
    private String selectedGrade;
    private String selectedTextbook;
    private String selectedTitle;
    private String pdfUrl;
    private String jsonAssetPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classroom, container, false);

        // Nhận dữ liệu từ HomeFragment
        if (getArguments() != null) {
            selectedGrade = getArguments().getString("grade");
            selectedTextbook = getArguments().getString("textbook");
            selectedTitle = getArguments().getString("title");
            pdfUrl = getArguments().getString("pdfUrl");
            jsonAssetPath = getArguments().getString("jsonAssetPath");

            // Hiển thị thông tin đã chọn
            String message = "Lớp học: " + selectedGrade + "\nSách: " + selectedTextbook;
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

            // TODO: Sử dụng các thông tin này để load nội dung lớp học
            loadClassroomContent();
        } else {
            // Nếu chưa chọn sách và lớp
            Toast.makeText(getContext(), "Vui lòng chọn sách và lớp học từ trang Home", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadClassroomContent() {
        // TODO: Implement logic để load nội dung lớp học dựa trên sách và lớp đã chọn
        // Ví dụ: load danh sách bài học, bài tập, v.v.
    }
}
