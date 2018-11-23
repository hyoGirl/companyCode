package com.rabbitmq.topic.CallBack;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback {




    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String exchangeName,String routingKey,String msg){

        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationId=new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(exchangeName,routingKey,msg,correlationId);
    }



    //消息到达exchange后触发回调
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String cause) {


//        System.out.println("消息确认到达了交换机，回调ID为："+correlationData.getId());

        if(b){
            System.out.println("成功！！消息发送到Exchange"+"回调ID为："+correlationData.getId());
        }else{
            System.out.println("失败！！消息发送到Exchange"+cause+correlationData.toString());
        }

    }




}
