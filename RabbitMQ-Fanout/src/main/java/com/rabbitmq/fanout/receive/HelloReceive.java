package com.rabbitmq.fanout.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloReceive {

    //监听不同的队列中的消息，然后做出不同的处理

    @RabbitListener(queues="Fanout.A")
    @RabbitHandler
    public void processA(String str) {

        System.out.println("ReceiveA:"+str);
    }


    @RabbitListener(queues="Fanout.B")
    @RabbitHandler
    public void processB(String str) {

        System.out.println("ReceiveB:"+str);
    }


    @RabbitListener(queues="Fanout.C")
    @RabbitHandler
    public void processC(String str) {

        System.out.println("ReceiveC:"+str);
    }
}
