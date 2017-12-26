package com.kortain.upsc.models;

/**
 * Created by satiswardash on 26/12/17.
 */

public class ConstraintRules {

    private int startId;
    private int startSide;
    private int endId;
    private int endSide;
    private int margin;

    public ConstraintRules(int startId, int startSide, int endId, int endSide, int margin) {
        this.startId = startId;
        this.startSide = startSide;
        this.endId = endId;
        this.endSide = endSide;
        this.margin = margin;
    }

    public int getStartId() {
        return startId;
    }

    public int getStartSide() {
        return startSide;
    }

    public int getEndId() {
        return endId;
    }

    public int getEndSide() {
        return endSide;
    }

    public int getMargin() {
        return margin;
    }
}