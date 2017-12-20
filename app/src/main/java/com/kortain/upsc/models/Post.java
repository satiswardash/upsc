package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class Post {

    private String id;
    private String userId;
    private String createdTs;
    private String title;
    private String description;
    private List<String> imageUri;
    private UserInteraction userInteraction;

    public Post() {
    }

    public Post(String id, String userId, String createdTs, String title, String description, List<String> imageUri, UserInteraction userInteraction) {
        this.id = id;
        this.userId = userId;
        this.createdTs = createdTs;
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;
        this.userInteraction = userInteraction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(String createdTs) {
        this.createdTs = createdTs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
    }

    public UserInteraction getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }
}
