package com.rabbitmq.topic2.Receive;

import com.rabbitmq.topic2.Listen.MyReceiveListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: Administrator
 * @Date: 2018/7/16 0016 17:07
 * @Description:
 */
@Component
public class HelloReceive {


    @Autowired
    @Qualifier("queue")
    private  Queue queue;

    @Autowired
    @Qualifier("secondQueue")
    private  Queue secondQueue;

    @Autowired
    private MyReceiveListener myReceiveListener;


    //消息接受端，实现消费确认监听
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainerQueue(ConnectionFactory connectionFactory){

        return getSimpleMessageListenerContainer(connectionFactory,queue);

    }


    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainersecondQueue(ConnectionFactory connectionFactory){

        return getSimpleMessageListenerContainer(connectionFactory,secondQueue);

    }


    private SimpleMessageListenerContainer getSimpleMessageListenerContainer(ConnectionFactory connectionFactory,Queue queue) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

        container.setQueues(queue);

        //设置为手动确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        container.setMessageListener(myReceiveListener);

        return container;
    }

}
