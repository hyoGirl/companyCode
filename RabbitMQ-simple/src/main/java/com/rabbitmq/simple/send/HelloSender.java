package com.rabbitmq.simple.send;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.simple.pojo.Msg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/7/13 0013 09:49
 * @Description:
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate template;

    //发送消息到指定的队列上
    public void send() {

        Map<String,String> paramsMap=new HashMap<String,String>();

        paramsMap.put("delete","11");


        String data = JSON.toJSONString(paramsMap);


        Msg msg=new Msg();

        msg.setCode(111);
        msg.setMessage("hello");


        //两个参数：1：队列名称  2：具体发送信息
        template.convertAndSend("Directqueue",msg.toString());
    }

    //测试一个发送端，多个接受端，看消息消费

    //结果：比较均匀的消费，同时也没有发生重复消费
    public void send(int count){

        String data="springBoot测试"+count;

        template.convertAndSend("Directqueue",data);

    }

}
