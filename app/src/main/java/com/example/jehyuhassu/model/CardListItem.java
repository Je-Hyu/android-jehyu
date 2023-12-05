package com.example.jehyuhassu.model;

public class CardListItem {
    private String image;
    private String name;
    private String time;
    private String tag;

    public CardListItem(String image, String name, String time, String tag) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getTag() {
        return tag;
    }
}
