package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class pdfViewer extends AppCompatActivity {

    private PDFView pdfView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        this.getSupportActionBar().hide();
        pdfView = findViewById(R.id.pdfView);
        url = getIntent().getStringExtra("pdfUrl");

        new viewPdf().execute(url);
    }

    private class viewPdf extends AsyncTask<String, Void, InputStream>{
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if(httpURLConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }

    }
}