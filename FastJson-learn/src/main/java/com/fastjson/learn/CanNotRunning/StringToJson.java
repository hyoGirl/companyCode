package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;

public class StringToJson {


    public static void main(String[] args) {


        String param="{\"studentName\":\"lily\",\"studentAge\":12}";


        System.out.println(JSON.toJSON(param));


    }
}
