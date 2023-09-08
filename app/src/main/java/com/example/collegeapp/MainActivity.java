package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;
    private int currentTab = 1;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView nav_drawer, user_photo;
    private RelativeLayout contentview;
    private DatabaseReference databaseReference;
    private String userName, userEmail, fakeurl = "null";
    private TextView user_name, user_email;
    Dialog dialog;
    private ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
    private FirebaseAuth fAuth;
    private FirebaseUser currentUser;
    private int margin=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        drawerLayout = findViewById(R.id.top_navigation);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        user_name = headerView.findViewById(R.id.user_name);
        user_email = headerView.findViewById(R.id.user_email);
        user_photo = headerView.findViewById(R.id.nav_profile);

        navigationView.setItemIconTintList(null);
        contentview = findViewById(R.id.main_page_content);

        if(currentUser == null){
            openLoginPage();
        }

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setElevation(20);
        View view = getSupportActionBar().getCustomView();


        nav_drawer = view.findViewById(R.id.nav_drawer);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbg));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button cancel = dialog.findViewById(R.id.AlertCancelBtn);
        Button logout = dialog.findViewById(R.id.AlertLogoutBtn);

        updateNavHeader();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });

        final LinearLayout home = findViewById(R.id.home);
        final LinearLayout notice = findViewById(R.id.notice);
        final LinearLayout faculty = findViewById(R.id.faculty);
        final LinearLayout gallery = findViewById(R.id.gallery);

        final ImageView homeImg = findViewById(R.id.homeImg);
        final ImageView noticeImg = findViewById(R.id.noticeImg);
        final ImageView facultyImg = findViewById(R.id.facultyImg);
        final ImageView galleryImg = findViewById(R.id.galleryImg);

        final TextView homeText = findViewById(R.id.homeText);
        final TextView noticeText = findViewById(R.id.noticeText);
        final TextView facultyText = findViewById(R.id.facultyText);
        final TextView galleryText = findViewById(R.id.galleryText);

        navigationDrawer();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer,homeFragment.class,null).commit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab != 1){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,homeFragment.class,null).commit();

                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    home.startAnimation(scaleAnimation);

                    noticeText.setVisibility(View.GONE);
                    facultyText.setVisibility(View.GONE);
                    galleryText.setVisibility(View.GONE);
                    homeText.setVisibility(View.VISIBLE);

                    noticeImg.setImageResource(R.drawable.ic_notice);
                    facultyImg.setImageResource(R.drawable.ic_faculty);
                    galleryImg.setImageResource(R.drawable.ic_gallery);
                    homeImg.setImageResource(R.drawable.ic_home_sel);

                    notice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    faculty.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    gallery.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    home.setBackgroundResource(R.drawable.round_back);


                    currentTab = 1;

                }
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,announcementFragment.class,null).commit();

                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    notice.startAnimation(scaleAnimation);

                    noticeText.setVisibility(View.VISIBLE);
                    facultyText.setVisibility(View.GONE);
                    galleryText.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);

                    noticeImg.setImageResource(R.drawable.ic_notice_sel);
                    facultyImg.setImageResource(R.drawable.ic_faculty);
                    galleryImg.setImageResource(R.drawable.ic_gallery);
                    homeImg.setImageResource(R.drawable.ic_home);

                    notice.setBackgroundResource(R.drawable.round_back);
                    faculty.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    gallery.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    home.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    currentTab = 2;

                }
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab != 3){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,facultyFragment.class,null).commit();

                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    faculty.startAnimation(scaleAnimation);

                    noticeText.setVisibility(View.GONE);
                    facultyText.setVisibility(View.VISIBLE);
                    galleryText.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);

                    noticeImg.setImageResource(R.drawable.ic_notice);
                    facultyImg.setImageResource(R.drawable.ic_faculty_sel);
                    galleryImg.setImageResource(R.drawable.ic_gallery);
                    homeImg.setImageResource(R.drawable.ic_home);

                    notice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    faculty.setBackgroundResource(R.drawable.round_back);
                    gallery.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    home.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    currentTab = 3;

                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab != 4){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,galleryFragment.class,null).commit();

                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    gallery.startAnimation(scaleAnimation);

                    noticeText.setVisibility(View.GONE);
                    facultyText.setVisibility(View.GONE);
                    galleryText.setVisibility(View.VISIBLE);
                    homeText.setVisibility(View.GONE);

                    noticeImg.setImageResource(R.drawable.ic_notice);
                    facultyImg.setImageResource(R.drawable.ic_faculty);
                    galleryImg.setImageResource(R.drawable.ic_gallery_sel);
                    homeImg.setImageResource(R.drawable.ic_home);

                    notice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    faculty.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    gallery.setBackgroundResource(R.drawable.round_back);
                    home.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    currentTab = 4;

                }
            }
        });

    }

    private void updateNavHeader() {
        databaseReference.child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    userData data = snapshot.getValue(userData.class);
                    userName = data.getName();
                    userEmail =  data.getEmail();
                    user_name.setText(userName);
                    user_email.setText(userEmail);
                    fakeurl = data.getUrl();

                    if (fakeurl.equals("null")){
                        user_photo.setImageResource(R.drawable.ic_profile_black);
                    }else {
                        try {
                            Picasso.get().load(data.getUrl()).into(user_photo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavHeader();
    }

    private void openLoginPage() {
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        nav_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animationToDrawer();
    }

    private void animationToDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float OffsetScale = 1-diffScaledOffset;
                contentview.setScaleX(OffsetScale);
                contentview.setScaleY(OffsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentview.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffset;
                contentview.setTranslationX(xTranslation);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else  {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile_detail:
                startActivity(new Intent(this,user_profile.class));
                break;

            case R.id.study_material:
                startActivity(new Intent(this,study_material.class));
                break;

            case R.id.contact_us:
                startActivity(new Intent(MainActivity.this,contact_us_page.class));
                break;

            case R.id.website:
                String url = "https://bmusurat.ac.in";
                Toast.makeText(this, "Opening Website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;

            case R.id.location:
                startActivity(new Intent(MainActivity.this,location_page.class));
                break;

            case R.id.logout:
                dialog.show();
                break;
        }
        return false;
    }

    private void signOutUser() {
        fAuth.signOut();
        openLoginPage();
    }
}