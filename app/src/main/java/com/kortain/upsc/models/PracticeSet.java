package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class PracticeSet {

    private String id;
    private List<PracticeQuestion> questionList;
    private String extraInfo;
    private int totalMarks;
    private int totalTime;
    private int totalNoOfQuestions;

    public PracticeSet() {
    }

    public PracticeSet(String id, List<PracticeQuestion> questionList, String extraInfo, int totalMarks, int totalTime, int totalNoOfQuestions) {
        this.id = id;
        this.questionList = questionList;
        this.extraInfo = extraInfo;
        this.totalMarks = totalMarks;
        this.totalTime = totalTime;
        this.totalNoOfQuestions = totalNoOfQuestions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PracticeQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<PracticeQuestion> questionList) {
        this.questionList = questionList;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalNoOfQuestions() {
        return totalNoOfQuestions;
    }

    public void setTotalNoOfQuestions(int totalNoOfQuestions) {
        this.totalNoOfQuestions = totalNoOfQuestions;
    }
}
