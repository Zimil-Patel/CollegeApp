package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class study_material extends AppCompatActivity {

    private ProgressBar progress;
    private DatabaseReference databaseReference;
    private RelativeLayout booksNoData;
    private RecyclerView booksRecycler;
    private MaterialAdapter adapter;
    private EditText searchText;

    private ImageView backBtn;

    private ArrayList<MaterialData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);

        this.getSupportActionBar().hide();

        backBtn = findViewById(R.id.backBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Study Material");

        booksNoData = findViewById(R.id.booksNoData);

        booksRecycler = findViewById(R.id.booksRecycler);

        progress = findViewById(R.id.progress);

        searchText = findViewById(R.id.searchText);

        getPdfData();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

    private void filter(String searchText) {
        ArrayList<MaterialData> filteredList = new ArrayList<>();
        for(MaterialData item : list){
            if(item.getPdfTitle().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(item);
            }
        }

        adapter.FilteredList(filteredList);
    }

    private void getPdfData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    progress.setVisibility(View.GONE);
                    booksNoData.setVisibility(View.VISIBLE);
                    booksRecycler.setVisibility(View.GONE);
                }else {
                    progress.setVisibility(View.GONE);
                    booksNoData.setVisibility(View.GONE);
                    booksRecycler.setVisibility(View.VISIBLE);
                    list = new ArrayList<>();
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        MaterialData data = snapshot1.getValue(MaterialData.class);
                        list.add(data);
                    }

                    booksRecycler.setHasFixedSize(true);
                    booksRecycler.setLayoutManager(new LinearLayoutManager(study_material.this,LinearLayoutManager.VERTICAL,false));
                    adapter = new MaterialAdapter(study_material.this,list);
                    booksRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(study_material.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}