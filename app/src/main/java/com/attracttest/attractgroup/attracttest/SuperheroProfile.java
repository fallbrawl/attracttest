package com.attracttest.attractgroup.attracttest;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nexus on 02.09.2017.
 */
public class SuperheroProfile implements Serializable {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public SuperheroProfile(int id, String name, String description, String image, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    int id;
    String name;
    String description;
    String image;
    String date;
}
