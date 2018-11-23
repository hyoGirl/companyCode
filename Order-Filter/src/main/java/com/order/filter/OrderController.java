package com.order.filter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


    @RequestMapping("/order")
    public String order(){

        return "hello";
    }

    @RequestMapping("/order2")
    public String order2(){

        return "hello2";
    }
}
