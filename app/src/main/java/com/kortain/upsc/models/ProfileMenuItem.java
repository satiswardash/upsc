package com.kortain.upsc.models;

import java.util.UUID;

/**
 * Created by satiswardash on 26/12/17.
 */

public class ProfileMenuItem {

    private String id;
    private int image;
    private String title;

    public ProfileMenuItem() {
    }

    public ProfileMenuItem(String id, int image, String title) {
        if(id == null){
            id = UUID.randomUUID().toString();
        }
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
