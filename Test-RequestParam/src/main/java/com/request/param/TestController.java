package com.request.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    //报错
    @PostMapping("/param")
    public Map<String,Object> param(@RequestBody Integer age,@RequestBody String name){

        Map<String,Object> param=new HashMap<>();
        param.put("age",age);
        param.put("name",name);


        String data1 = JSON.toJSONString(param);


        Map<String,Object> result=new HashMap<>();

        result.put("msg","操作成功");
        result.put("data",data1);


        return  result;


    }

    @PostMapping("/param2")
    public Map<String,Object> param2(@RequestBody User user){

        Map<String,Object> param=new HashMap<>();


        param.put("age",user.getAge());
        param.put("name",user.getName());


        String data1 = JSON.toJSONString(param);


        Map<String,Object> result=new HashMap<>();

        result.put("msg","操作成功");
        result.put("data",data1);


        return  result;


    }
    @PostMapping("/param3")
    public Map<String,Object> param3(@RequestBody String data){


        JSONObject jsonObject = JSONObject.parseObject(data);

        Map<String,Object> result=(Map<String,Object>)jsonObject;


        return  result;


    }
    //报错
    @PostMapping("/param4")
    public Map<String,Object> param4(@RequestBody Integer age,@RequestBody String name){

        Map<String,Object> param4=new HashMap<>();
        param4.put("age",age);
        param4.put("name",name);

        return  param4;


    }
}
