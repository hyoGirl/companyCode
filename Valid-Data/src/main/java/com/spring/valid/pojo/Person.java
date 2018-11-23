package com.spring.valid.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

public class Person implements Serializable {


    private int age;

//   @DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS")

//   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
//   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")  //结果
//    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
     //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//这个不行
    private Date time;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", time=" + time +
                '}';
    }
}
