package com.app.pug.models;

import android.widget.ImageView;

import java.util.ArrayList;

public class UpcomingPlayedItem {

    public String dateTime;
    public String location;
    public int joined;
    public int left;
    public ArrayList<ImageView> images;

    public UpcomingPlayedItem() {
        this("", "", 0, 10, new ArrayList<ImageView>());
    }

    public UpcomingPlayedItem(String dateTime, String location, int joined, int left, ArrayList<ImageView> images) {
        this.dateTime = dateTime;
        this.location = location;
        this.joined = joined;
        this.left = left;
        this.images = images;
    }
}
