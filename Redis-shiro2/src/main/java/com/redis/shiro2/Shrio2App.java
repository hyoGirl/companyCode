package com.redis.shiro2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value="com.redis.shiro2.dao")
public class Shrio2App {


    public static void main(String[] args) {

        SpringApplication.run(Shrio2App.class, args);

    }

//    @Autowired
//    private RestTemplateBuilder restTemplateBuilder;
//
//    @Bean
//    public RestTemplate restTemplate(){
//        return restTemplateBuilder.build();
//    }
}
