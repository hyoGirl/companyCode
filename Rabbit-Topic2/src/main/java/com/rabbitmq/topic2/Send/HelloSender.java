package com.rabbitmq.topic2.Send;

import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    //消息发送端实现发送确认机制
    public void send(String topic, String message) {
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        this.rabbitTemplate.convertAndSend("topic2Exchange", topic, message, correlationData);
    }


    //消息到达exchange后触发回调
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息id:" + correlationData.getId()+"消息发送确认成功");
        } else {
            System.out.println("消息发送确认失败:" + cause);
        }
    }
}
 /*
    //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    //ack返回false，并重新回到队列，api里面解释得很清楚
    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    //拒绝消息
    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    如果消息没有到exchange,则confirm回调,ack=false
    如果消息到达exchange,则confirm回调,ack=true
    exchange到queue成功,则不回调return
    exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
    */