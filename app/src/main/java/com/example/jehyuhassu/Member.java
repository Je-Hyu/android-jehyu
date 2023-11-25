package com.example.jehyuhassu;

public class Member {
    private String studentNum;
    private String department;
    private String name;
    private String major;

    public Member() {
        // 기본 생성자는 Firebase Firestore에서 객체를 복원할 때 필요합니다.
    }

    public Member(String studentNum, String department, String name, String major) {
        this.studentNum = studentNum;
        this.department = department;
        this.name = name;
        this.major = major;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}

