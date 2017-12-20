package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class Subjective extends Question {

    private String answer;

    public Subjective() {
    }

    public Subjective(String id, List<String> imageUri, String question, String information, String answer) {
        super(id, imageUri, question, information);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
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
