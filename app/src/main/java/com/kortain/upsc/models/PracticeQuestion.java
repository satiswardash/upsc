package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class PracticeQuestion extends Question {

    private List<String> options;
    private int objectiveAnswer;
    private int timeLimit;
    private int mark;

    public PracticeQuestion() {
    }

    public PracticeQuestion(String id, List<String> imageUri, String question, String information, List<String> options, int objectiveAnswer, int timeLimit, int mark) {
        super(id, imageUri, question, information);
        this.options = options;
        this.objectiveAnswer = objectiveAnswer;
        this.timeLimit = timeLimit;
        this.mark = mark;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getObjectiveAnswer() {
        return objectiveAnswer;
    }

    public void setObjectiveAnswer(int objectiveAnswer) {
        this.objectiveAnswer = objectiveAnswer;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
