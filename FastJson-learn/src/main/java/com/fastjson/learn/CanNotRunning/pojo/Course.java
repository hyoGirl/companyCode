package com.fastjson.learn.CanNotRunning.pojo;

import java.io.Serializable;

public class Course implements Serializable{

    private static final long serialVersionUID = 7837934522512054657L;


    private String courseName;
    private Integer code;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", code=" + code +
                '}';
    }
}
