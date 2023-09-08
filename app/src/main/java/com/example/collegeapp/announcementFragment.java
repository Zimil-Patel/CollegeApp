package com.example.collegeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcementFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView announcement;
    private DatabaseReference databaseReference;
    private ArrayList<announcementData> list;
    private announcementAdapter adapter;
    private RelativeLayout noData;

    public announcementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_announcement, container, false);

        announcement = v.findViewById(R.id.noticeRecyclerView);
        progressBar = v.findViewById(R.id.progressBar);
        noData = v.findViewById(R.id.nullData);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notice Data");

        announcementRecycler();
        return v;

    }

    private void announcementRecycler() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    announcement.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                    noData.setVisibility(View.GONE);
                    announcement.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        announcementData data = snapshot.getValue(announcementData.class);
                        list.add(data);
                    }
                    announcement.setHasFixedSize(true);
                    announcement.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
                    adapter = new announcementAdapter(getContext(), list);
                    announcement.setAdapter(adapter);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}