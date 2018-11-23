package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;
import com.fastjson.learn.CanNotRunning.pojo.Student;

import java.util.List;

public class JSONArrayStringToBean {

    public static void main(String[] args) {



        String param="[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";


        List<Student> students = JSON.parseArray(param, Student.class);

        for (Student student : students) {

            System.out.println(student);
        }

    }
}
