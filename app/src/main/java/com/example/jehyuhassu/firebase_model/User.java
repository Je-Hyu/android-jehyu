package com.example.jehyuhassu.firebase_model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("studentId")
    private String studentId;
    @SerializedName("password")
    private String pw;
    @SerializedName("name")
    private String name;
    @SerializedName("college")
    private String college;
    @SerializedName("department")
    private String department;
    public User(String studentId, String pw, String name, String college, String department){
        this.studentId=studentId;
        this.pw=pw;
        this.name=name;
        this.college=college;
        this.department=department;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public void setPassword(String pw) {
        this.pw = pw;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudentId() {
        return this.studentId;
    }
    public String getPassword() {
        return this.pw;
    }
    public String getName() {
        return this.name;
    }
    public String getCollege() {
        return this.college;
    }
    public String getDepartment() {
        return this.department;
    }
}