package com.kortain.upsc.firestore_models;

/**
 * Created by satiswardash on 04/01/18.
 */

public class Scoreboard {

    private String information;
    private Integer rank;
    private Integer score;

    public Scoreboard() {
    }

    public Scoreboard(String information, Integer rank, Integer score) {
        this.information = information;
        this.rank = rank;
        this.score = score;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
