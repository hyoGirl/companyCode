package com.fastjson.learn.CanNotRunning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonStringToArray {


    public static void main(String[] args) {


        /**
         * 复杂的json格式的字符串，转换为数组形式
         */

        String param="[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";


        JSONArray objects = JSON.parseArray(param);

        for(int i=0;i<objects.size();i++){

            //里面是一个个对象
            JSONObject jsonObject = objects.getJSONObject(i);
            System.out.println(jsonObject.getString("studentName") + ": " + jsonObject.getString("studentName"));

        }

        System.out.println("**************************************");

        //因为JSON和jsonArray以及JsonObject的关系，所以还有其他方法处理
        JSONArray objects1 = JSONArray.parseArray(param);

        for (Object obj: objects1) {

            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject.getString("studentName") + ": " + jsonObject.getString("studentName"));


        }
    }
}
