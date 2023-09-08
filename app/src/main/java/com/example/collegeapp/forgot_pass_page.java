package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class forgot_pass_page extends AppCompatActivity {

    private EditText login_email;
    private TextView login_link;
    private String email;
    private CircularProgressButton cirSendButton;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_page);

        this.getSupportActionBar().hide();

        fAuth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_link = findViewById(R.id.loginLink);
        cirSendButton = findViewById(R.id.cirSendButton);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });

        cirSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = login_email.getText().toString();
                if(!(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                    login_email.setError("Enter Valid Email");
                    login_email.requestFocus();
                } else {
                    cirSendButton.startAnimation();
                    sendRestPassMail();
                }
            }
        });
    }

    private void sendRestPassMail() {
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    cirSendButton.revertAnimation();
                    Toast.makeText(forgot_pass_page.this, "Password reset link sent", Toast.LENGTH_SHORT).show();
                    openLoginPage();
                } else {
                    cirSendButton.revertAnimation();
                    Toast.makeText(forgot_pass_page.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openLoginPage() {
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }
}