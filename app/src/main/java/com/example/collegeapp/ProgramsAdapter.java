package com.example.collegeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.programViewAdapter> {

    private Context context;
    private ArrayList<ProgramsData> list;

    public ProgramsAdapter(Context context, ArrayList<ProgramsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProgramsAdapter.programViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.program_list_ui,parent,false);
        return new programViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramsAdapter.programViewAdapter holder, int position) {
        holder.ugName.setText(list.get(position).getName());
        holder.ugIntake.setText("Intake : "+list.get(position).getIntake());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class programViewAdapter extends RecyclerView.ViewHolder {

        private TextView ugName, ugIntake;
        public programViewAdapter(@NonNull View itemView) {
            super(itemView);
            ugName = itemView.findViewById(R.id.ugName);
            ugIntake = itemView.findViewById(R.id.ugIntake);
        }
    }
}
