package com.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.datasource.dao")
public class App04 {


    public static void main(String[] args) {


        SpringApplication.run(App04.class,args);
    }
}
