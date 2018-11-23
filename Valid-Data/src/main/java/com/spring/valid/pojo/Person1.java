package com.spring.valid.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class Person1 implements Serializable{


    private static final long serialVersionUID = -4679722513835358458L;
    private int age;

//   @DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS")

    //   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
   // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String time;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Person1{" +
                "age=" + age +
                ", time='" + time + '\'' +
                '}';
    }
}
