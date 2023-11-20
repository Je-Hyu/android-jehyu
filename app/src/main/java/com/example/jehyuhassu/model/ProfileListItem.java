package com.example.jehyuhassu.model;

public class ProfileListItem {
    private String title;
    private String content;

    public ProfileListItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
