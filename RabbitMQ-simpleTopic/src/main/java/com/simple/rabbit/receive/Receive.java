package com.simple.rabbit.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receive {




    @RabbitListener(queues="simpleTopic.A")
    public void process1(String str) {



        System.out.println("simpleTopic.A:"+str);


    }

    @RabbitListener(queues="simpleTopic.B")
    public void process2(String str) {

        try {
            int a=100/0;
            System.out.println("simpleTopic.B:"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
