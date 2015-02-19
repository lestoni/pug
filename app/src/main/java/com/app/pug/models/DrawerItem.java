package com.app.pug.models;

/**
 * Created by MATIVO-PC on 2/19/2015, 9:45 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class DrawerItem {
    private int icon;
    private String title;
    private boolean isExpandable;
    private String[] subItems;

    public DrawerItem(int icon, String title, boolean isExpandable, String[] subItems){
        setIcon(icon);
        setTitle(title);
        setExpandable(isExpandable);
        setSubItems(subItems);
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

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean isExpandable) {
        this.isExpandable = isExpandable;
    }

    public String[] getSubItems() {
        return subItems;
    }

    public void setSubItems(String[] subItems) {
        this.subItems = subItems;
    }
}
