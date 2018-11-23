package com.business.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.business.log.dao")
public class BuinessLogApp {

    public static void main(String[] args) {


        SpringApplication.run(BuinessLogApp.class,args);

    }
}
