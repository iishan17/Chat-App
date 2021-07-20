package com.example.chatme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.Models.Chats;
import com.example.chatme.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter {

    private static final int SENDER_VIEW_TYPE = 1;
    private static final int RECEIVER_VIEW_TYPE = 2;
    private Context context;
    private ArrayList<Chats> mList;

    public ChatsAdapter(Context context, ArrayList<Chats> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resources;
        if(viewType == SENDER_VIEW_TYPE){
            resources = R.layout.sender_list_view;
        }
        else{
            resources = R.layout.receiver_list_view;
        }
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chats chats = mList.get(position);
        ((ChatViewHolder)holder).message.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private TextView time;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text_view);
            time = itemView.findViewById(R.id.time_text_view);
        }
    }
}
