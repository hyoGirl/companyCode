package com.mybatis.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mybatis.log.dao")
public class MybatisApp {

    public static void main(String[] args) {


        SpringApplication.run(MybatisApp.class,args);

    }
}
