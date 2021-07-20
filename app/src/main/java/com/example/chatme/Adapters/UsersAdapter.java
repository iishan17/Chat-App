package com.example.chatme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.ChatActivity;
import com.example.chatme.Models.Users;
import com.example.chatme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<Users> mList;
    Context context;

    public UsersAdapter(ArrayList<Users> mList, Context context){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_view, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Users users = mList.get(position);
        Picasso.get().load(users.getPhotoUrl()).placeholder(R.drawable.profile_default_image).into(holder.mProfileImageView);
        holder.mNameTextView.setText(users.getName());
        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid())
                .child(users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                holder.mLastMessage.setText(dataSnapshot.child("message").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId", users.getUserId());
                intent.putExtra("photoUrl", users.getPhotoUrl());
                intent.putExtra("name", users.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder  {

        ImageView mProfileImageView;
        TextView mNameTextView;
        TextView mLastMessage;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mProfileImageView = itemView.findViewById(R.id.profile_image_view);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mLastMessage = itemView.findViewById(R.id.last_message);
        }


    }
}