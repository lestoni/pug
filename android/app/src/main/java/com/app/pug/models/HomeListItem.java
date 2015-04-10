package com.app.pug.models;

/**
 * Created by MATIVO-PC on 2/23/2015, 1:34 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class HomeListItem {
    private int icon;
    private String name, role, location;
    private int plays, followers, following;

    /**
     * Constructor
     *
     * @param icon      Item Image
     * @param name      Players Name
     * @param role      Players Role
     * @param location  Players Location
     * @param plays     Number of plays
     * @param followers Number of followers
     * @param following Number of Number player follows
     */
    public HomeListItem(int icon, String name, String role, String location, int plays, int followers, int following) {
        setIcon(icon);
        setName(name);
        setRole(role);
        setLocation(location);
        setPlays(plays);
        setFollowers(followers);
        setFollowing(following);
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public int getPlays() {
        return plays;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
}
