package com.kortain.upsc.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by satiswardash on 27/12/17.
 */

public class HomeSubMenuItem {

    private String id;
    private List<HashMap<String, List<String>>> subItemsWithId;

    public HomeSubMenuItem() {
    }

    public HomeSubMenuItem(String id, List<HashMap<String, List<String>>> subItemsWithId) {
        this.id = id;
        this.subItemsWithId = subItemsWithId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<HashMap<String, List<String>>> getSubItemsWithId() {
        return subItemsWithId;
    }

    public void setSubItemsWithId(List<HashMap<String, List<String>>> subItemsWithId) {
        this.subItemsWithId = subItemsWithId;
    }
}
