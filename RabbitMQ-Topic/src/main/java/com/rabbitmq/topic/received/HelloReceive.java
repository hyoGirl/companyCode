package com.rabbitmq.topic.received;


import com.rabbitmq.topic.CallBack.ReceiveConfirmListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class HelloReceive {

    //监听第一个队列
    @RabbitListener(queues="topic.msg")
    @RabbitHandler
    public void process1(String str) {


        System.out.println("第一个队列接受到的message:"+str);
    }

    //监听第二个队列
    @RabbitListener(queues="topic.msgs")
    @RabbitHandler
    public void process2( String str) {

        System.out.println("第二个队列接受到的messages:"+str);
    }

}
