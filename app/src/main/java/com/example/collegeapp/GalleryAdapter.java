package com.example.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewAdapter> {
    @NonNull
    private Context context;
    private ArrayList<GalleryData> list;

    public GalleryAdapter(@NonNull Context context, ArrayList<GalleryData> list) {
        this.context = context;
        this.list = list;
    }

    public GalleryAdapter.GalleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_ui,parent,false);
        return new GalleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewAdapter holder, int position) {
        Glide.with(context).load(list.get(position).getImgUrl()).into(holder.photoView);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,fullScreenView.class);
                intent.putExtra("image",list.get(position).getImgUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GalleryViewAdapter extends RecyclerView.ViewHolder {
        ImageView photoView;
        public GalleryViewAdapter(@NonNull View itemView) {
            super(itemView);

            photoView = itemView.findViewById(R.id.photoView);
        }
    }
}
