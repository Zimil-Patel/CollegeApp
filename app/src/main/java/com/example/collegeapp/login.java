package com.example.collegeapp;

import static com.example.collegeapp.Register.PASSWORD_PATTERN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class login extends AppCompatActivity {

    private EditText login_email, login_password;
    private TextView registerLink, forgotLink;
    private CircularProgressButton cirLoginButton;
    private FirebaseAuth fAuth;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        fAuth = FirebaseAuth.getInstance();

        this.getSupportActionBar().hide();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(login.this,MainActivity.class));
            finish();
        }

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        registerLink = findViewById(R.id.registerLink);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        forgotLink = findViewById(R.id.forgotLink);

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassPage();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableString content = new SpannableString("Register here");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                registerLink.setText(content);

                openRegisterPage();
            }
        });

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputsVerification();

            }
        });
    }

    private void openForgotPassPage() {
        startActivity(new Intent(login.this,forgot_pass_page.class));
    }

    private void openMainPage() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private void openRegisterPage() {
        login_email.setText(null);
        login_password.setText(null);
        startActivity(new Intent(login.this,Register.class));
    }

    private void inputsVerification() {
        email = login_email.getText().toString().trim();
        password = login_password.getText().toString().trim();
        if (!(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            login_email.setError("Enter Valid email");
            login_email.requestFocus();
        }  else if (password.isEmpty()){
            login_password.setError("Enter Strong Password");
            login_password.requestFocus();
        }   else {
            cirLoginButton.startAnimation();
            loginUser();
        }
    }

    private void loginUser() {
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(fAuth.getCurrentUser().isEmailVerified()){
                                cirLoginButton.revertAnimation();
                                openMainPage();
                            }else {
                                cirLoginButton.revertAnimation();
                                fAuth.signOut();
                                Toast.makeText(login.this, "First verify your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },1000);
                }else {
                    cirLoginButton.revertAnimation();
                    Toast.makeText(login.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}