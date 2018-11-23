package com.fastjson.learn.CanNotRunning.pojo;

import java.io.Serializable;

public class Student implements Serializable{

    private static final long serialVersionUID = 1034187981280944339L;

    private String studentName;
    private Integer studentAge;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentAge=" + studentAge +
                '}';
    }
}