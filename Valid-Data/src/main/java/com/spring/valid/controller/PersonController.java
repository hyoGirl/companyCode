package com.spring.valid.controller;

import com.alibaba.fastjson.JSON;
import com.spring.valid.pojo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PersonController {

    @PostMapping("/person")
    public  Object test1(@RequestBody Person person){
        System.out.println(person);

        System.out.println(person.getTime().getTime());

        return JSON.toJSON(person);
    }


    @PostMapping("/person1")
    public  Object test1(@RequestBody Person1 person1){
        System.out.println(person1);

        return JSON.toJSON(person1);
    }


    @PostMapping("/person3")
    public  Object test3(@RequestBody Person3 person1){
        System.out.println(person1);

        return JSON.toJSON(person1);
    }
    @PostMapping("/call")
    public  Object test3(@RequestBody CallRecord callRecord){

        Long timestamp = callRecord.getTimestamp();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        String format = simpleDateFormat.format(new Date(timestamp));


        System.out.println(format);


        System.out.println(callRecord);

        return JSON.toJSON(callRecord);
    }

    @PostMapping("/call2")
    public  Object call2(@RequestBody CallRecord2 callRecord){



        System.out.println(callRecord);

        return JSON.toJSON(callRecord);
    }
}
