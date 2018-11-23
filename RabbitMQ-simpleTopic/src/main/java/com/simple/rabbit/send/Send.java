package com.simple.rabbit.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Send {

    @Autowired
    private AmqpTemplate template;


    //发送消息到指定的队列上
    public void send(String topic ,String paramData) {


        //三个参数：1：交换机名称  2：发送的路由key  3：参数发送的内容


        template.convertAndSend("simpleTopicExchange",topic,paramData);

        //测试发送集合数据
        // template.convertAndSend("exchange", "topic.msg",mapdata);


    }


}
