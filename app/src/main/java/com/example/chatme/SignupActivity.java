package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatme.Models.Users;
import com.example.chatme.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();
    ActivitySignupBinding binding;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        mProgressDialog = new ProgressDialog(SignupActivity.this);
        mProgressDialog.setTitle("Creating account");
        mProgressDialog.setMessage("we are creating your account");

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                mFirebaseAuth.createUserWithEmailAndPassword
                        (binding.emailView.getText().toString(), binding.passwordView.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Users user = new Users(binding.usernameView.getText().toString(),
                                            binding.nameView.getText().toString(),
                                            binding.emailView.getText().toString(),
                                            binding.passwordView.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    mDatabaseReference.child("Users").child(id).setValue(user);
                                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.signInNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}