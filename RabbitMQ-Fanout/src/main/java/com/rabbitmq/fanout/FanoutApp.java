package com.rabbitmq.fanout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FanoutApp {

    public static void main(String[] args) {

        SpringApplication.run(FanoutApp.class,args);

    }
}
