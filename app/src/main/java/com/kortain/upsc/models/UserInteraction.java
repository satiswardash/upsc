package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class UserInteraction {

    private String id;
    private int likes;
    private int dislikes;
    private List<Comment> comments;

    public UserInteraction() {
    }

    public UserInteraction(String id, int likes, int dislikes, List<Comment> comments) {
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
