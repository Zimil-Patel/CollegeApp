package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class fullScreenView extends AppCompatActivity {

    private PhotoView fullImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);

        this.getSupportActionBar().hide();

        fullImageView = findViewById(R.id.fullImageView);

        String imageurl = getIntent().getStringExtra("image");

        Glide.with(this).load(imageurl).into(fullImageView);
    }
}