package com.example.study_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study_app.R;
import com.example.study_app.adapter.ChatAdapter;
import com.example.study_app.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private Button buttonSend;
    private ChatAdapter chatAdapter;
    private List<Message> messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerViewChat = view.findViewById(R.id.recyclerViewChat);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChat.setAdapter(chatAdapter);

        buttonSend.setOnClickListener(v -> sendMessage());

        // Thêm tin nhắn chào mừng
        addBotMessage("Xin chào! Tôi là chatbot hỗ trợ học tập. Bạn có thể hỏi tôi về bài học hoặc các vấn đề học tập.");

        return view;
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            addUserMessage(messageText);
            editTextMessage.setText("");

            // Simulate bot response
            simulateBotResponse(messageText);
        }
    }

    private void addUserMessage(String text) {
        Message message = new Message(text, true);
        messages.add(message);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        recyclerViewChat.scrollToPosition(messages.size() - 1);
    }

    private void addBotMessage(String text) {
        Message message = new Message(text, false);
        messages.add(message);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        recyclerViewChat.scrollToPosition(messages.size() - 1);
    }

    private void simulateBotResponse(String userMessage) {
        // Simple bot response simulation
        String response;
        String lowerMessage = userMessage.toLowerCase();

        if (lowerMessage.contains("toán") || lowerMessage.contains("math")) {
            response = "Tôi có thể giúp bạn với các bài toán. Bạn có thể hỏi về phép tính, hình học, hoặc đại số.";
        } else if (lowerMessage.contains("văn") || lowerMessage.contains("literature")) {
            response = "Tôi có thể hỗ trợ bạn về văn học, ngữ pháp, và kỹ năng viết.";
        } else if (lowerMessage.contains("hello") || lowerMessage.contains("xin chào")) {
            response = "Xin chào! Tôi rất vui được hỗ trợ bạn học tập hôm nay.";
        } else {
            response = "Tôi hiểu bạn đang hỏi về: \"" + userMessage + "\". Bạn có thể cung cấp thêm chi tiết để tôi hỗ trợ tốt hơn không?";
        }

        // Delay to simulate thinking
        new android.os.Handler().postDelayed(() -> addBotMessage(response), 1000);
    }
}
