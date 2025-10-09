package com.example.study_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.study_app.R;

public class QuizFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Hiển thị thông báo chức năng đang phát triển
        Toast.makeText(getContext(), "Chức năng Quiz đang được phát triển", Toast.LENGTH_SHORT).show();

        return view;
    }
}
