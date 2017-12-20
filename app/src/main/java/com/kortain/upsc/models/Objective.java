package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class Objective extends Question {


    private List<String> options;
    private int answer;

    public Objective() {
    }

    public Objective(String id, List<String> imageUri, String question, String information, List<String> options, int answer) {
        super(id, imageUri, question, information);
        this.options = options;
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
