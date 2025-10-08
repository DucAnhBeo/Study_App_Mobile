package adapter;

import model.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myapplication.R;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_USER = 1;
    private static final int VIEW_TYPE_BOT = 2;

    private ArrayList<Message> messageList;

    public MessageAdapter(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    public int getItemViewType(int position) {
        Message msg = messageList.get(position);
        if ("user".equals(msg.getSender())) {
            return VIEW_TYPE_USER;
        } else {
            return VIEW_TYPE_BOT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_bot, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = messageList.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).bind(msg);
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).bind(msg);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;

        UserViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.text_message);
        }

        void bind(Message msg) {
            txtMessage.setText(msg.getText());
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;

        BotViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.text_message);
        }

        void bind(Message msg) {
            txtMessage.setText(msg.getText());
        }
    }
}

