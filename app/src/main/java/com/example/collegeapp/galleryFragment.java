package com.example.collegeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class galleryFragment extends Fragment {

    RecyclerView memories;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("College Gallery");;
    View nodata;
    GalleryAdapter adapter;
    ArrayList<GalleryData> list;


    public galleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        memories = v.findViewById(R.id.memoriesRecycler);
        nodata = v.findViewById(R.id.Nodata);

        memories();
        return v;
    }

    private void memories() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if (!snapshot.exists()){
                    nodata.setVisibility(View.VISIBLE);
                    memories.setVisibility(View.GONE);
                } else {
                    nodata.setVisibility(View.GONE);
                    memories.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        GalleryData data = snapshot1.getValue(GalleryData.class);
                        list.add(data);
                    }
                    memories.setHasFixedSize(true);
                    adapter = new GalleryAdapter(getContext(),list);
                    memories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    memories.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}