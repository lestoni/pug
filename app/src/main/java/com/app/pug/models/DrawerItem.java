package com.app.pug.models;

/**
 * Created by MATIVO-PC on 2/19/2015, 9:45 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class DrawerItem {
    private int icon;
    private String title;

    public static enum TYPE {EXPANDABLE, NOTIFICATION, GALLERY, NORMAL};

    private TYPE type;
    private int notifications;
    private String[] subItems;

    public DrawerItem(int icon, String title, String[] subItems, int numNotifications, TYPE type){
        setIcon(icon);
        setTitle(title);
        setSubItems(subItems);
        setNotifications(numNotifications);
        setType(type);
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getSubItems() {
        return subItems;
    }

    public void setSubItems(String[] subItems) {
        this.subItems = subItems;
    }
}
