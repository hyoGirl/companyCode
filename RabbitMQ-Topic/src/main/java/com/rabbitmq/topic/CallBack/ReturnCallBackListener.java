package com.rabbitmq.topic.CallBack;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service("returnCallBackListener")
public class ReturnCallBackListener implements RabbitTemplate.ReturnCallback{

    /**
     * 1:exchange发送消息到queue成功就不会回调
     * 2：发送失败会触发回调
     * exchange到queue成功,则不回调return
     *
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
     *
     */


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
    }
}
