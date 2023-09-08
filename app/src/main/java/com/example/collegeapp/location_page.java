package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class location_page extends AppCompatActivity implements View.OnClickListener {

    private ImageView backBtn;
    private CircleImageView facebook, insta, twitter, linkedin;
    private CardView map;
    private TextView numberOne, numberTwo, numberThree, numberFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);

        this.getSupportActionBar().hide();

        backBtn = findViewById(R.id.backBtn);
        map = findViewById(R.id.map);

        numberOne = findViewById(R.id.numberOne);
        numberTwo = findViewById(R.id.numberTwo);
        numberThree = findViewById(R.id.numberThree);
        numberFour = findViewById(R.id.numberFour);

        facebook = findViewById(R.id.facebook_link);
        insta = findViewById(R.id.insta_link);
        twitter = findViewById(R.id.twitter_link);
        linkedin = findViewById(R.id.linkedin_link);


        backBtn.setOnClickListener(this);
        map.setOnClickListener(this);

        numberOne.setOnClickListener(this);
        numberTwo.setOnClickListener(this);
        numberThree.setOnClickListener(this);
        numberFour.setOnClickListener(this);

        facebook.setOnClickListener(this);
        insta.setOnClickListener(this);
        twitter.setOnClickListener(this);
        linkedin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Intent i2 = new Intent(Intent.ACTION_VIEW);
        switch (view.getId()){
            case R.id.numberOne:
                intent.setData(Uri.parse("tel:+91 2616770101"));
                startActivity(intent);
                break;

            case R.id.numberTwo:
                intent.setData(Uri.parse("tel:+91 2616770102"));
                startActivity(intent);
                break;

            case R.id.numberThree:
                intent.setData(Uri.parse("tel:+91 2616770103"));
                startActivity(intent);
                break;

            case R.id.numberFour:
                intent.setData(Uri.parse("tel:+91 2616770104"));
                startActivity(intent);
                break;

            case R.id.backBtn:
                onBackPressed();
                break;

            case R.id.map:
                Uri uri = Uri.parse("geo:0, 0?q=Bhagwan Mahavir University, Bharthana Road, Vesu, Surat, Gujarat");
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
                break;

            case R.id.facebook_link:
                i2.setData(Uri.parse("https://www.facebook.com/BhagwanMahavirUniversity/"));
                startActivity(i2);
                break;

            case R.id.insta_link:
                i2.setData(Uri.parse("https://www.instagram.com/bhagwanmahaviruniversity/"));
                startActivity(i2);
                break;

            case R.id.twitter_link:
                i2.setData(Uri.parse("https://twitter.com/bmusurat?s=20"));
                startActivity(i2);
                break;

            case R.id.linkedin_link:
                i2.setData(Uri.parse("https://www.linkedin.com/company/bmusurat/%20"));
                startActivity(i2);
                break;
        }
    }
}