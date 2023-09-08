package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class user_profile extends AppCompatActivity {

    private ImageView backBtn, profile_photo;
    private TextView profile_email, profile_gender, saveBtn,  edit_Photo, choosePhoto, removePhoto;
    private EditText profile_name;
    private String name, email, user_id, gender, downloadUrl = "null", tmp = "false";
    private FirebaseAuth fAuth;
    private Bitmap bitmap;
    private final int request = 1;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progress;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        this.getSupportActionBar().hide();

        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        storageReference = FirebaseStorage.getInstance().getReference().child("users_photo");

        progress = new ProgressDialog(this);
        progress.setTitle("Saving info...");
        progress.setMessage("Please wait");

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_gender = findViewById(R.id.profile_gender);
        profile_photo = findViewById(R.id.profilePhoto);
        saveBtn = findViewById(R.id.saveBtn);
        edit_Photo = findViewById(R.id.editPhoto);
        backBtn = findViewById(R.id.backBtn);

        dialog = new Dialog(user_profile.this);
        dialog.setContentView(R.layout.profile_edit_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbg));
        dialog.setCancelable(true);

        choosePhoto = dialog.findViewById(R.id.choosePhoto);
        removePhoto = dialog.findViewById(R.id.removePhoto);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openGallery();
            }
        });

        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                profile_photo.setImageResource(R.drawable.ic_profile);
                bitmap = null;
                tmp = "true";
            }
        });

        databaseReference.child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData data = snapshot.getValue(userData.class);

                email = data.getEmail();
                gender = data.getGender();
                user_id = fAuth.getCurrentUser().getUid();
                downloadUrl = data.getUrl();

                profile_name.setText(data.getName());
                profile_email.setText(data.getEmail());
                profile_gender.setText(data.getGender());

                if(downloadUrl.equals("null")){
                    profile_photo.setImageResource(R.drawable.ic_profile);
                }else{
                    try {
                        Picasso.get().load(data.getUrl()).into(profile_photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = profile_name.getText().toString();

                if(name.isEmpty()){
                    profile_name.setError("please enter name");
                    profile_name.requestFocus();
                } else if (tmp.equals("true")){
                    progress.show();
                    downloadUrl = "null";
                    dataUpload();
                } else if (tmp.equals("false") && bitmap != null){
                    progress.show();
                    imageUpload();
                } else {
                    progress.show();
                    dataUpload();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void dataUpload() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("user_id",user_id);
        hashMap.put("gender",gender);
        hashMap.put("url",downloadUrl);

        databaseReference.child(fAuth.getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progress.dismiss();
                Toast.makeText(user_profile.this, "Information saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(user_profile.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImg,request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == request && resultCode == RESULT_OK){
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                tmp = "false";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            profile_photo.setImageBitmap(bitmap);
        }
    }

    private void imageUpload() {

        ByteArrayOutputStream img = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,img);
        byte[] finalImage = img.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);

        uploadTask.addOnCompleteListener(user_profile.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    dataUpload();
                                }
                            });
                        }
                    });
                } else{
                    progress.dismiss();
                    Toast.makeText(user_profile.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}