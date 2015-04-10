package com.app.pug.models;

import java.io.Serializable;

/**
 * Created by MATIVO-PC on 2/23/2015, 3:59 PM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class OpenGameItem implements Serializable {
    private int icon;
    private String name, location, date, status;
    private int aside, mins, amount;

    public OpenGameItem(int icon, String name, String location, String date, int aside, int mins, int amount, String status) {
        setIcon(icon);
        setName(name);
        setLocation(location);
        setDate(date);
        setAside(aside);
        setMins(mins);
        setAmount(amount);
        setStatus(status);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAside(int aside) {
        this.aside = aside;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }


    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getAside() {
        return aside;
    }

    public int getIcon() {
        return icon;
    }

    public int getMins() {
        return mins;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
