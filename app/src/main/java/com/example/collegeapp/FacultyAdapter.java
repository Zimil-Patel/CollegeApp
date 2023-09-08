package com.example.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.facultyViewAdapter> {

    private ArrayList<FacultyData> list;
    private Context context;

    public FacultyAdapter(ArrayList<FacultyData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FacultyAdapter.facultyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_ui, parent, false);
        return new facultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAdapter.facultyViewAdapter holder, int position) {
        FacultyData item = list.get(position);
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        holder.email.setText(item.getEmail());

        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
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

    public class facultyViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, post, email;
        private CircleImageView imageView;

        public facultyViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personName);
            post = itemView.findViewById(R.id.personPost);
            email = itemView.findViewById(R.id.personEmail);
            imageView = itemView.findViewById(R.id.personPhoto);
        }
    }
}
