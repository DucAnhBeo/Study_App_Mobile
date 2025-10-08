package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myapplication.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView numberQuestion;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            numberQuestion = itemView.findViewById(R.id.txtNumber);
        }
    }
}
