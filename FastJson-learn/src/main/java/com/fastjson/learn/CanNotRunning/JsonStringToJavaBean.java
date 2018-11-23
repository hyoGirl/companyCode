package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fastjson.learn.CanNotRunning.pojo.Student;

public class JsonStringToJavaBean {


    public static void main(String[] args) {


        String param="{\"studentName\":\"lily\",\"studentAge\":12}";

        Student student = JSON.parseObject(param, Student.class);

        System.out.println(student.getStudentName());
        System.out.println(student.getStudentAge());

        Student jsonObject = JSONObject.parseObject(param,Student.class);


        System.out.println(jsonObject.getStudentAge()+": "+jsonObject.getStudentName());


        System.out.println(JSON.toJSONString(jsonObject));


    }


}
