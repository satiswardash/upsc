package com.kortain.upsc.models;

/**
 * Created by satiswardash on 27/12/17.
 */

public class HomeMenuItem {

    private String id;
    private String name;
    private int image;

    public HomeMenuItem() {
    }

    public HomeMenuItem(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
