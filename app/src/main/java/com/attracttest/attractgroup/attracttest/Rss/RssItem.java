package com.attracttest.attractgroup.attracttest.Rss;

/**
 * Created by paul on 05.09.17.
 */

public class RssItem {
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public RssItem (String title, String description){
        this.title = title;
        this.description = description;
    }

    private String title;
    private String description;
}
