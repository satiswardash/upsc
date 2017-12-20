package com.kortain.upsc.models;

import com.google.firebase.auth.FirebaseAuth;
import com.kortain.upsc.utils.FirebaseUtility;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class UserInformation {

    private String uid;
    private String displayName;
    private String email;
    private String phone;
    private String pictureUrl;
    private Scoreboard scoreboard;
    private List<Post> collections;
    private List<Question> bookmarks;

    public UserInformation() {
    }

    public UserInformation(String uid, String displayName, String email, String phone, String pictureUrl, Scoreboard scoreboard, List<Post> collections, List<Question> bookmarks) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.pictureUrl = pictureUrl;
        this.scoreboard = scoreboard;
        this.collections = collections;
        this.bookmarks = bookmarks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public List<Post> getCollections() {
        return collections;
    }

    public void setCollections(List<Post> collections) {
        this.collections = collections;
    }

    public List<Question> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Question> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
