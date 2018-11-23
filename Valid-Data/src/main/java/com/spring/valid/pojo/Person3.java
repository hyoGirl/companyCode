package com.spring.valid.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Person3 implements Serializable{


    private static final long serialVersionUID = 1835059116221411725L;


    private int age;

    private Long time;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date occurTime;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    @Override
    public String toString() {
        return "Person3{" +
                "age=" + age +
                ", time=" + time +
                ", occurTime=" + occurTime +
                '}';
    }
}
