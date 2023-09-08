package com.example.collegeapp;

import android.content.Context;

import java.util.ArrayList;

public class GalleryData {
    private String imgUrl;

    public GalleryData() {
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public GalleryData(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
