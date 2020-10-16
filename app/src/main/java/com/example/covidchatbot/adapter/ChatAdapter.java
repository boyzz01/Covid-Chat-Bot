package com.example.covidchatbot.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidchatbot.R;
import com.example.covidchatbot.model.ChatModel;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter < ChatAdapter.ViewHolder> {

    public static final int leftMessage = 0;
    public static final  int rightMessage = 1;

    private Context context;
    private List<ChatModel> chatList;
    public CircleImageView avatarImage;


    public ChatAdapter(Context context, List<ChatModel> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public  ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == leftMessage) {
            View view = LayoutInflater.from(context).inflate(R.layout.left_message_layout, parent, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.right_message_layout, parent, false);

            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.ViewHolder holder, int position) {

       final ChatModel chat = chatList.get(position);

        holder.message.setText(chat.getMessage());
        holder.time.setText(chat.getTime());
    }





    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message,time;

        public ViewHolder(View itemView) {
            super(itemView);

           message = itemView.findViewById(R.id.messageText);
           time = itemView.findViewById(R.id.timeText);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (chatList.get(position).getSender().equals("user"))
        {
            return rightMessage;
        }
        else
        {
            return  leftMessage;
        }
    }
}