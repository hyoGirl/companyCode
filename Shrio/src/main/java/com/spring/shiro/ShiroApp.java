package com.spring.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ShiroApp {

    public static void main(String[] args) {


        SpringApplication.run(ShiroApp.class,args);

    }
}
