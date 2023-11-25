package com.example.jehyuhassu;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    boolean success;

    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    StudentData studentData;

    static class StudentData {
        @SerializedName("dept")
        DeptData deptData;
        @SerializedName("parentDept")
        ParentDeptData parentDeptData;

        //학번
        @SerializedName("memberNo")
        String memberNo;

        //이름
        @SerializedName("name")
        String name;
    }

    static class DeptData {
        @SerializedName("name")
        String name;
    }

    static class ParentDeptData {
        @SerializedName("id")
        int id;
        @SerializedName("code")
        String code;
        @SerializedName("name")
        String name;
    }

    public StudentData getStudentData() {
        return studentData;
    }

    public String getDept() {
        return studentData.deptData.name;
    }

    public String getParentDept() {
        return studentData.parentDeptData.name;
    }

    public String getName() {
        return studentData.name;
    }

    public String getStudentNum() {
        return studentData.memberNo;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
