package com.rabbitmq.simple.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Administrator
 * @Date: 2018/7/13 0013 09:44
 * @Description:
 */
@Configuration
public class SenderConf {


    @Bean
    public Queue queue() {

        return new Queue("Directqueue");
    }




}
