package com.rabbitmq.simple.received;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: Administrator
 * @Date: 2018/7/13 0013 09:50
 * @Description:
 */
@Component
//使用这个注解表示这个类是rabbitmq的消息处理类
@RabbitListener(queues="Directqueue")
public class HelloReceive {

    //说明这个方法是具体的消息处理方法
    @RabbitHandler
    public void processC(String str) {

        System.out.println("第一个Receive:"+str);
    }





}
