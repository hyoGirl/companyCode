package com.request.param;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DateController {


    @PostMapping("/date")
    public Map<String,Object> param(@RequestBody DatePerson datePerson) throws Exception {


        System.out.println(datePerson);
        System.out.println("Date类型序列化后的时间："+datePerson.getTime()+"  Date类型序列化后的时间毫秒数目是："+datePerson.getTime().getTime());
        System.out.println("String类型的时间： "+datePerson.getTime2());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date parse = simpleDateFormat.parse(datePerson.getTime2());


        System.out.println("String类型的时间序列化后时间为： "+parse+" 毫秒数目为："+parse.getTime());


        Map<String,Object> param=new HashMap<>();
        String format = simpleDateFormat.format(datePerson.getTime());

        param.put("time",format);
        param.put("time2",datePerson.getTime2());
        param.put("name",datePerson.getName());
        return  param;




    }
}
