package com.example.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewAdapter> {

    private Context context;
    private ArrayList<MaterialData> list;

    public MaterialAdapter(Context context, ArrayList<MaterialData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MaterialAdapter.MaterialViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.study_material_ui,parent,false);
        return new MaterialViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialAdapter.MaterialViewAdapter holder, int position) {

        holder.pdfName.setText(list.get(position).getPdfTitle());

        holder.openPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Loading Please Wait", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,pdfViewer.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });
        
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void FilteredList(ArrayList<MaterialData> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public class MaterialViewAdapter extends RecyclerView.ViewHolder {

        private Button downloadBtn;
        private TextView pdfName;
        private CardView openPdf;
        public MaterialViewAdapter(@NonNull View itemView) {
            super(itemView);

            downloadBtn = itemView.findViewById(R.id.downloadBtn);
            pdfName = itemView.findViewById(R.id.pdfName);
            openPdf = itemView.findViewById(R.id.openPDF);
        }
    }
}
