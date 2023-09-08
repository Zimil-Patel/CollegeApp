package com.example.collegeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class facultyFragment extends Fragment {

    private DatabaseReference databaseReference, dbRef;
    private ProgressBar progressBarBca, progressBarMca, progressBarImca;
    private ArrayList<FacultyData> l1, l2, l3;
    private FacultyAdapter adapter;
    private RelativeLayout noDataBca, noDataMca, noDataImca;
    private RecyclerView bcaRecycler, mcaRecycler, imcaRecycler;

    public facultyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_faculty, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculties Detail");
        progressBarBca = v.findViewById(R.id.progressBarBca);
        progressBarMca = v.findViewById(R.id.progressBarMca);
        progressBarImca = v.findViewById(R.id.progressBarImca);

        noDataBca = v.findViewById(R.id.bcaNoData);
        noDataMca = v.findViewById(R.id.mcaNoData);
        noDataImca = v.findViewById(R.id.imcaNoData);

        bcaRecycler = v.findViewById(R.id.bcaRecycler);
        mcaRecycler = v.findViewById(R.id.mcaRecycler);
        imcaRecycler = v.findViewById(R.id.imcaRecycler);

        bcaDepartment();
        mcaDepartment();
        imcaDepartment();

        return v;
    }

    private void imcaDepartment() {
        dbRef = databaseReference.child("IMCA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l3 = new ArrayList<>();
                if(!snapshot.exists()){
                    progressBarImca.setVisibility(View.GONE);
                    noDataImca.setVisibility(View.VISIBLE);
                    imcaRecycler.setVisibility(View.GONE);
                } else {
                    progressBarImca.setVisibility(View.GONE);
                    noDataImca.setVisibility(View.GONE);
                    imcaRecycler.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data = snapshot1.getValue(FacultyData.class);
                        l3.add(data);
                    }
                    imcaRecycler.setHasFixedSize(true);
                    imcaRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    adapter = new FacultyAdapter(l3, getContext());
                    imcaRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mcaDepartment() {
        dbRef = databaseReference.child("MCA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l2 = new ArrayList<>();
                if(!snapshot.exists()){
                    progressBarMca.setVisibility(View.GONE);
                    noDataMca.setVisibility(View.VISIBLE);
                    mcaRecycler.setVisibility(View.GONE);
                } else {
                    progressBarMca.setVisibility(View.GONE);
                    noDataMca.setVisibility(View.GONE);
                    mcaRecycler.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data = snapshot1.getValue(FacultyData.class);
                        l2.add(data);
                    }
                    mcaRecycler.setHasFixedSize(true);
                    mcaRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    adapter = new FacultyAdapter(l2, getContext());
                    mcaRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bcaDepartment() {
        dbRef = databaseReference.child("BCA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l1 = new ArrayList<>();
                if(!snapshot.exists()){
                    progressBarBca.setVisibility(View.GONE);
                    noDataBca.setVisibility(View.VISIBLE);
                    bcaRecycler.setVisibility(View.GONE);
                } else {
                    progressBarBca.setVisibility(View.GONE);
                    noDataBca.setVisibility(View.GONE);
                    bcaRecycler.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data = snapshot1.getValue(FacultyData.class);
                        l1.add(data);
                    }
                    bcaRecycler.setHasFixedSize(true);
                    bcaRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    adapter = new FacultyAdapter(l1, getContext());
                    bcaRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}