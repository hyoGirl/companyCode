package com.rabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Administrator
 * @Date: 2018/7/13 0013 09:36
 * @Description:
 */
@SpringBootApplication
public class SimpleApp {


    static Logger logger = LoggerFactory.getLogger(SimpleApp.class);

    public static void main(String[] args) {


        SpringApplication.run(SimpleApp.class,args);


        logger.info("【【【【【消息队列-消息提供者启动成功.】】】】】");

    }
}
