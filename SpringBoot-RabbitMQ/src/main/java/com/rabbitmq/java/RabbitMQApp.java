package com.rabbitmq.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Administrator
 * @Date: 2018/7/10 0010 14:00
 * @Description:
 */
@SpringBootApplication
public class RabbitMQApp {

    static Logger logger = LoggerFactory.getLogger(RabbitMQApp.class);

    public static void main(String[] args) {


        SpringApplication.run(RabbitMQApp.class,args);


        logger.info("【【【【【消息队列-消息提供者启动成功.】】】】】");

    }
}
