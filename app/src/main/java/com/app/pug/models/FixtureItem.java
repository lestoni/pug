package com.app.pug.models;

import java.io.Serializable;

/**
 * Created by MATIVO-PC on 3/4/2015, 12:19 AM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class FixtureItem implements Serializable{
    private String team1, team2, time, playground;
    private  int icon_team_1, icon_team_2;
    private int numJoined, numSpotsLeft;

    /**
     *
     * @param team_1 Team 1 Name
     * @param team_2 Team 2 Name
     * @param time Time for the fixture
     * @param playground Playground Venue
     * @param icon_t1 Icon for team 1
     * @param icon_t2 Icon for team 2
     * @param joined Number Joined
     * @param spotsLeft Spots Left
     */
    public FixtureItem(String team_1, String team_2, String time, String playground, int icon_t1, int icon_t2, int joined, int spotsLeft) {
        setTeam1(team_1);
        setTeam2(team_2);
        setTime(time);
        setPlayground(playground);
        setIcon_team_1(icon_t1);
        setIcon_team_2(icon_t2);
        setNumJoined(joined);
        setNumSpotsLeft(spotsLeft);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIcon_team_1(int icon_team_1) {
        this.icon_team_1 = icon_team_1;
    }

    public void setIcon_team_2(int icon_team_2) {
        this.icon_team_2 = icon_team_2;
    }

    public void setNumJoined(int numJoined) {
        this.numJoined = numJoined;
    }

    public void setNumSpotsLeft(int numSpotsLeft) {
        this.numSpotsLeft = numSpotsLeft;
    }

    public void setPlayground(String playground) {
        this.playground = playground;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTime() {
        return time;
    }

    public int getIcon_team_1() {
        return icon_team_1;
    }

    public int getIcon_team_2() {
        return icon_team_2;
    }

    public int getNumJoined() {
        return numJoined;
    }

    public int getNumSpotsLeft() {
        return numSpotsLeft;
    }

    public String getPlayground() {
        return playground;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

}
