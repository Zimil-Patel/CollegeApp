package com.example.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class announcementAdapter extends RecyclerView.Adapter<announcementAdapter.announcmentView> {


    private Context context;
    private ArrayList<announcementData> list;

    public announcementAdapter(Context context, ArrayList<announcementData> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public announcementAdapter.announcmentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.annoucement_ui,parent,false);
        return new announcmentView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull announcementAdapter.announcmentView holder, int position) {
        announcementData item = list.get(position);
        holder.noticeTitle.setText(item.getTitle());
        holder.noticeTime.setText(item.getTime());
        holder.noticeDate.setText(item.getDate());
        holder.noticeImage.setVisibility(View.GONE);
        holder.imageViewDivider.setVisibility(View.GONE);

        try {
            Picasso.get().load(item.getImage()).into(holder.noticeImage);
            holder.noticeImage.setVisibility(View.VISIBLE);
            holder.imageViewDivider.setVisibility(View.VISIBLE);

            holder.noticeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,fullScreenView.class);
                    intent.putExtra("image",item.getImage());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class announcmentView extends RecyclerView.ViewHolder {
        private TextView noticeDate, noticeTitle, noticeTime;
        private ImageView noticeImage;
        private View imageViewDivider;
        public announcmentView(@NonNull View itemView) {
            super(itemView);
            noticeDate = itemView.findViewById(R.id.noticeDate);
            noticeImage = itemView.findViewById(R.id.noticeImage);
            noticeTime = itemView.findViewById(R.id.noticeTime);
            noticeTitle = itemView.findViewById(R.id.noticeTtle);
            imageViewDivider = itemView.findViewById(R.id.imageViewDivider);
        }
    }
}
