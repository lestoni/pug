package com.app.pug.models;

/**
 * Created by MATIVO-PC on 3/4/2015, 12:28 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class FixtureModel {
    private String time;
    private FixtureItem[] items;

    public FixtureModel(String time, FixtureItem...items) {
        this.time = time;
        this.items = items;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public FixtureItem[] getItems() {
        return items;
    }

    public void setItems(FixtureItem...items) {
        this.items = items;
    }

    public boolean hasChild() {
        if(items == null) return false;
        if(items.length > 0) return true;
        else return false;
    }
}
