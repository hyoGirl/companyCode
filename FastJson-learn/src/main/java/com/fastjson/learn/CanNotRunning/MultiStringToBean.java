package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MultiStringToBean {


    public static void main(String[] args) {


        String param= "{\"teacherName\":\"crystall\",\"teacherAge\":27," +
                "\"course\":{\"courseName\":\"english\",\"code\":1270}," +
                "\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";


        JSONObject jsonObject =JSON.parseObject(param);


        String teacherName = jsonObject.getString("teacherName");

        Integer teacherAge = jsonObject.getInteger("teacherAge");

        JSONObject course = jsonObject.getJSONObject("course");

        JSONArray students = jsonObject.getJSONArray("students");


        System.out.println(teacherName);


        System.out.println(teacherAge);


        System.out.println(course);


        for (Object student : students) {

            System.out.println(student);
        }


    }
}
