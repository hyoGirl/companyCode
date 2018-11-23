package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;
import com.fastjson.learn.CanNotRunning.pojo.Student;

public class BeanToString {

    public static void main(String[] args) {


        Student student=new Student();


        student.setStudentAge(22);

        student.setStudentName("AAA");


        System.out.println(JSON.toJSONString(student));


        System.out.println(JSON.toJSON(student));
    }
}
