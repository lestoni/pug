package com.app.pug.models;

import android.text.Spanned;

/**
 * Created by MATIVO-PC on 2/23/2015, 1:34 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class UserListItem {
    private int icon;
    private Spanned text;
    private String time;

    /**
     * Constructor
     *
     * @param icon      Item Image
     * @param text
     */
    public UserListItem(int icon, Spanned text, String time) {
        setIcon(icon);
        setText(text);
        setTime(time);
    }

    public int getIcon() {
        return icon;
    }


    public Spanned getText() {
        return text;
    }

    public void setText(Spanned text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
