package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.chatme.Adapters.ChatsAdapter;
import com.example.chatme.Models.Chats;
import com.example.chatme.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding mBinding;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Chats> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Chats");
        getSupportActionBar().hide();
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String senderId = mFirebaseAuth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String profilePic = getIntent().getStringExtra("photoUrl");
        String name = getIntent().getStringExtra("name");

        mBinding.nameTextView.setText(name);
        Picasso.get().load(profilePic).placeholder(R.drawable.profile_default_image).into(mBinding.profileImageView);

        ChatsAdapter adapter = new ChatsAdapter(this, mList);
        mBinding.chatRecyclerView.setAdapter(adapter);
        mBinding.chatRecyclerView.setHasFixedSize(true);
        mBinding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = mBinding.messageEditView.getText().toString();
                String encryptedMessage = null;
                try {
                    encryptedMessage = AES.encrypt(message);
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
                Chats chats = new Chats(senderId, encryptedMessage);
                chats.setTimestamp(new Date().getTime());
                mDatabaseReference.child(senderId).child(receiverId).push().setValue(chats)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mBinding.messageEditView.setText("");
                                mDatabaseReference.child(receiverId).child(senderId).push().setValue(chats)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });
            }
        });

        mDatabaseReference.child(senderId).child(receiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Chats chats = data.getValue(Chats.class);
                    Chats newChats = null;

                    String message = null;
                    try {
                        message = AES.decrypt(chats.getMessage());
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    }
                    newChats = new Chats(chats.getUid(), message);

                    mList.add(newChats);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}