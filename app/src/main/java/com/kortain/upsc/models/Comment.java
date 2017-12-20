package com.kortain.upsc.models;

/**
 * Created by satiswardash on 20/12/17.
 */

public class Comment {

    private String id;
    private String commentDescription;
    private UserInteraction userInteraction;

    public Comment() {
    }

    public Comment(String id, String commentDescription, UserInteraction userInteraction) {
        this.id = id;
        this.commentDescription = commentDescription;
        this.userInteraction = userInteraction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public UserInteraction getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }
}
