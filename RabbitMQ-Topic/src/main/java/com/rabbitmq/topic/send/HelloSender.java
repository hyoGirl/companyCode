package com.rabbitmq.topic.send;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.topic.CallBack.ConfirmCallBackListener;
import com.rabbitmq.topic.pojo.TopicMsg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: Administrator
 * @Date: 2018/7/16 0016 09:51
 * @Description:
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate template;



    @Autowired
    ConfirmCallBackListener confirmCallBackListener;

    //发送消息到指定的队列上
    public void send(String topic ,String paramData) {


        String msg = "hello topic";
        //三个参数：1：交换机名称  2：发送的路由key  3：参数发送的内容

        //MQ会根据第二个参数去寻找有没有匹配此规则的队列

//        template.convertAndSend("exchange","topic.message",msg);

        //换一种路由key来发送，现在只有路由key是topic.# 的可以接受了
//        template.convertAndSend("exchange","topic.ceshi",msg);


        TopicMsg topicMsg = new TopicMsg();

        topicMsg.setCode(111);
        topicMsg.setMessage("测试topic发送对象");


        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("delete", "16");

        String mapdata = JSON.toJSONString(paramsMap);

        //测试发送对象信息,对象必须转换为字符串
//        template.convertAndSend("exchange", "topic.msg", topicMsg.toString());

        confirmCallBackListener.send("topic1exchange", topic,paramData);


        //测试发送集合数据
       // template.convertAndSend("exchange", "topic.msg",mapdata);


    }

}
