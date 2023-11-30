package com.example.jehyuhassu.firebase_model;

public class User {
    private String studentId, id, pw, name, college, department;
    public User(String id, String pw, String studentId, String name, String college, String department){
        this.id=id;
        this.pw=pw;
        this.studentId=studentId;
        this.name=name;
        this.college=college;
        this.department=department;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getId() {
        return this.id;
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