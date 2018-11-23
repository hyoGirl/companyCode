package com.filter.learn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FilterController {


    @GetMapping("/test")
    public  String test1(){
        return "token";
    }


    @GetMapping("/test2")
    public  String test2(){
        return "code";
    }
    @GetMapping("/test3")
    public  String test3(){
        return "test3";
    }



}
