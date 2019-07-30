package com.jaywhitsitt.eggsandbakey.data;

import java.io.Serializable;

public class Step implements Serializable {

    private int id;
    private String shortDescription;
    private String description;
//    private String videoUrl;
//    private String thumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
//        this.videoUrl = videoUrl;
//        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }
}
