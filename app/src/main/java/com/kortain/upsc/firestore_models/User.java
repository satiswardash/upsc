package com.kortain.upsc.firestore_models;

import com.google.firebase.firestore.DocumentReference;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.helpers.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by satiswardash on 04/01/18.
 */

public class User {

    private String displayName;
    private String email;
    private String phone;
    private String pictureUri;
    private Scoreboard scoreboard;
    private DocumentReference[] bookmarks;
    private DocumentReference[] collections;
    private DocumentReference[] posts;

    public User() {
    }

    public User(String displayName, String email, String phone, String pictureUri, Scoreboard scoreboard, DocumentReference[] bookmarks, DocumentReference[] collections, DocumentReference[] posts) {
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.scoreboard = scoreboard;
        this.bookmarks = bookmarks;
        this.collections = collections;
        this.posts = posts;
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

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public DocumentReference[] getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(DocumentReference[] bookmarks) {
        this.bookmarks = bookmarks;
    }

    public DocumentReference[] getCollections() {
        return collections;
    }

    public void setCollections(DocumentReference[] collections) {
        this.collections = collections;
    }

    public DocumentReference[] getPosts() {
        return posts;
    }

    public void setPosts(DocumentReference[] posts) {
        this.posts = posts;
    }

    public static User convertFromMap(Map<String, Object> objectMap) {
        User user = new User();

        if (objectMap.containsKey(Constants.AUTH_USER_DISPLAY_NAME)) {
            user.setDisplayName(objectMap.get(Constants.AUTH_USER_DISPLAY_NAME).toString());
        }
        if (objectMap.containsKey(Constants.AUTH_USER_EMAIL)) {
            user.setEmail(objectMap.get(Constants.AUTH_USER_EMAIL).toString());
        }
        if (objectMap.containsKey(Constants.AUTH_USER_PHONE)) {
            user.setPhone(objectMap.get(Constants.AUTH_USER_PHONE).toString());
        }
        if (objectMap.containsKey(Constants.AUTH_USER_PICTURE)) {
            user.setPictureUri(objectMap.get(Constants.AUTH_USER_PICTURE).toString());
        }
        if (objectMap.containsKey(Constants.AUTH_USER_SCOREBOARD)) {
            user.setScoreboard((Scoreboard) objectMap.get(Constants.AUTH_USER_SCOREBOARD));
        }
        if (objectMap.containsKey(Constants.AUTH_USER_PHONE)) {
            user.setPhone(objectMap.get(Constants.AUTH_USER_PHONE).toString());
        }
        if (objectMap.containsKey(Constants.AUTH_USER_PHONE)) {
            user.setPhone(objectMap.get(Constants.AUTH_USER_PHONE).toString());
        }
        return user;
    }

    public static Map<String, Object> convertFromDto(String displayName, String email, String password, String phone, String pictureUri, Scoreboard scoreboard, DocumentReference[] bookmarks, DocumentReference[] collections, DocumentReference[] posts){
        Map<String, Object> userDto = new HashMap<>();
        userDto.put(Constants.AUTH_USER_DISPLAY_NAME, displayName);
        userDto.put(Constants.AUTH_USER_PHONE, phone);
        userDto.put(Constants.AUTH_USER_EMAIL, email);
        userDto.put(Constants.AUTH_USER_PASSWORD, password);
        return userDto;
    }
}
