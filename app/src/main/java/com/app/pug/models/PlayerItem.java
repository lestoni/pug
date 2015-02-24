package com.app.pug.models;

/**
 * Created by MATIVO-PC on 2/23/2015, 11:40 PM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class PlayerItem {
    private int icon;
    private String name, role, joined, time;

    public PlayerItem(int icon, String name, String role, String joined, String time) {
        setName(name);
        setIcon(icon);
        setRole(role);
        setJoined(joined);
        setTime(time);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getJoined() {
        return joined;
    }

    public String getRole() {
        return role;
    }

    public String getTime() {
        return time;
    }
}
