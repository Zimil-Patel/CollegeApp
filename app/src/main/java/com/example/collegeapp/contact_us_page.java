package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class contact_us_page extends AppCompatActivity{

    private ImageView backBtn;
    private TextView AdmissionNum1, AdmissionNum2, AdmissionNum3;
    private CircularProgressButton cirSubmitBtn;
    private EditText first_name, last_name, form_email, form_subject, form_message;
    private DatabaseReference databaseReference;
    private String name,firstname, lastname, email, subject, message, status = "not approved";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_page);

        this.getSupportActionBar().hide();

        backBtn = findViewById(R.id.backBtn);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        form_email = findViewById(R.id.form_email);
        form_subject = findViewById(R.id.form_subject);
        form_message = findViewById(R.id.form_message);

        cirSubmitBtn = findViewById(R.id.cirSubmitButton);

        AdmissionNum1 = findViewById(R.id.AdmissionNumberOne);
        AdmissionNum2 = findViewById(R.id.AdmissionNumberTwo);
        AdmissionNum3 = findViewById(R.id.AdmissionNumberThree);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("requests");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cirSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInformation();
            }
        });

        AdmissionNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 2616770115"));
                startActivity(intent);
            }
        });

        AdmissionNum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 7575807374"));
                startActivity(intent);
            }
        });

        AdmissionNum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 7575807375"));
                startActivity(intent);
            }
        });
    }

    private void checkInformation() {
        firstname = first_name.getText().toString();
        lastname = last_name.getText().toString();
        email = form_email.getText().toString();
        subject = form_subject.getText().toString();
        message = form_message.getText().toString();

        if(firstname.isEmpty()){
            first_name.setError("Enter Name");
            first_name.requestFocus();
        } else if(lastname.isEmpty()){
            last_name.setError("Enter Name");
            last_name.requestFocus();
        } else if(!(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            form_email.setError("Enter Email");
            form_email.requestFocus();
        }  else if(subject.isEmpty()){
            form_subject.setError("Enter Subject");
            form_subject.requestFocus();
        } else if (message.isEmpty()) {
            form_message.setError("Enter Message");
            form_message.requestFocus();
        } else {
            name = firstname +" "+ lastname;
            cirSubmitBtn.startAnimation();
            sendRequest();
        }

    }

    private void sendRequest() {

        final String primaryKey = databaseReference.push().getKey();
        requestsData data = new requestsData(name, email, subject, message, status, primaryKey);

        databaseReference.child(primaryKey).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(contact_us_page.this, "Request successfully sent", Toast.LENGTH_SHORT).show();
                first_name.setText(null);
                last_name.setText(null);
                form_email.setText(null);
                form_subject.setText(null);
                form_message.setText(null);
                cirSubmitBtn.revertAnimation();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contact_us_page.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                first_name.setText(null);
                last_name.setText(null);
                form_email.setText(null);
                form_subject.setText(null);
                form_message.setText(null);
                cirSubmitBtn.revertAnimation();
            }
        });

    }
}