package com.example.jehyuhassu.model;

public class CardListItem {
    private int image;
    private String name;
    private String time;
    private String[] tags;

    public CardListItem(int image, String name, String time, String[] tags) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.tags = tags;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String[] getTags() {
        return tags;
    }
}
