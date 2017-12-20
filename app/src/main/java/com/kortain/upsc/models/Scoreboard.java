package com.kortain.upsc.models;

/**
 * Created by satiswardash on 20/12/17.
 */

public class Scoreboard {

    private String uid;
    private String score;
    private String information;

    public Scoreboard() {
    }

    public Scoreboard(String uid, String score, String information) {
        this.uid = uid;
        this.score = score;
        this.information = information;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
