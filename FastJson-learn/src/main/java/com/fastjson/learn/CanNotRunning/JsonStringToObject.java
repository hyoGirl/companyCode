package com.fastjson.learn.CanNotRunning;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonStringToObject {


    //json字符串-简单对象型与JSONObject之间的转换


    public static void main(String[] args) {


        String data="{\"age\":\"24\",\"name\":\"cool_summer_moon\"}";

        String param="{\"studentName\":\"lily\",\"studentAge\":12}";


        /**
         *  JSON：fastJson的解析器，用于JSON格式字符串与JSON对象及javaBean之间的转换。
         *  JSONObject：fastJson提供的json对象。可以理解为一个map。加强版的map
         *  JSONArray：fastJson提供json数组对象。可以理解为一个增强版的list
         *
         *
         *  这三者之间的关系是： JSONOBJECT 和JSONArray都继承了JSON
         *
         *  因为map或者是对象在转换为json的时候，都是kv对，所以反过来kv类似的string在转换json的时候也有很多方法
         *
       */

        JSONObject jsonObject = JSON.parseObject(param);
        System.out.println(jsonObject);

        //因为JSONObject继承了JSON，所以这样也是可以的
        JSONObject jsonObject1 = JSONObject.parseObject(param);

        System.out.println(jsonObject1);


    }
}
