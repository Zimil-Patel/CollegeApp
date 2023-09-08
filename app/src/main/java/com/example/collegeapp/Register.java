package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Register extends AppCompatActivity {


    private EditText reg_name, reg_email, reg_password;
    private String name,email,password;
    private RadioGroup radioGroup;
    private RadioButton radioButton, radio_male, radio_female;
    private CircularProgressButton cirRegisterButton;
    private String Gender, downloadUrl = "null";
    private TextView loginLink;
    private FirebaseAuth fAuth;
    private DatabaseReference db;
    private String userId;
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.getSupportActionBar().hide();
        cirRegisterButton = findViewById(R.id.cirRegisterButton);
        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        radioGroup = findViewById(R.id.radioGroup);
        loginLink = findViewById(R.id.loginLink);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("users");

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableString content = new SpannableString("Login here");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                loginLink.setText(content);

                openLoginPage();
            }
        });

        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);

        cirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputsVerification();
            }
        });


    }

    private void openLoginPage() {
        onBackPressed();
//        startActivity(new Intent(Register.this,login.class));
        finish();
    }

    private void inputsVerification() {
        email = reg_email.getText().toString().trim();
        password = reg_password.getText().toString().trim();
        if ((name = reg_name.getText().toString()).isEmpty()){
            reg_name.setError("Enter Name");
            reg_name.requestFocus();
        }  else if (!(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            reg_email.setError("Enter Valid email");
            reg_email.requestFocus();
        }  else if (!(!password.isEmpty() && PASSWORD_PATTERN.matcher(password).matches())){
            reg_password.setError("Enter Strong Password");
            reg_password.requestFocus();
        } else if (!radio_male.isChecked() && !radio_female.isChecked()) {
            Toast.makeText(this, "Please Select Gender!", Toast.LENGTH_SHORT).show();
        }  else {
            cirRegisterButton.startAnimation();
            registerUser();
        }
    }

    private void registerUser() {
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userId = fAuth.getCurrentUser().getUid();
                    HashMap<String,Object> data = new HashMap<>();
                    data.put("name",name);
                    data.put("email",email);
                    data.put("gender",Gender);
                    data.put("user_id",userId);
                    data.put("url",downloadUrl);

                    db.child(userId).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Error!" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    sendVerificationMail();

                } else {
                    cirRegisterButton.revertAnimation();
                    Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationMail() {
        fAuth.getCurrentUser().
                sendEmailVerification().
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(Register.this, "Registration Successful, Verification mail Sent", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cirRegisterButton.revertAnimation();
                        fAuth.signOut();
                        openLoginPage();
                    }
                },1000);

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Registration Failed,Verification mail not sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Gender = radioButton.getText().toString();
    }

}