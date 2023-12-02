package com.example.jehyuhassu.firebase_model;

import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("participants")
    private int participants;
    @SerializedName("storeName")
    private String storeName;
    @SerializedName("location")
    private String location;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("college")
    private String college;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("contents")
    private String contents;
    @SerializedName("menu1")
    private String menu1;
    @SerializedName("menu2")
    private String menu2;
    @SerializedName("menu3")
    private String menu3;

    public Store(int participants, String storeName, String location, String startTime, String endTime, String college, String startDate, String endDate, String contents, String menu1, String menu2, String menu3) {
        this.participants = participants;
        this.storeName = storeName;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.college = college;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.menu1 = menu1;
        this.menu2 = menu2;
        this.menu3 = menu3;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setMenu1(String menu1) {
        this.menu1 = menu1;
    }

    public void setMenu2(String menu2) {
        this.menu2 = menu2;
    }

    public void setMenu3(String menu3) {
        this.menu3 = menu3;
    }

    public int getParticipants() {
        return participants;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCollege() {
        return college;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getContents() {
        return contents;
    }

    public String getMenu1() {
        return menu1;
    }

    public String getMenu2() {
        return menu2;
    }

    public String getMenu3() {
        return menu3;
    }
}
