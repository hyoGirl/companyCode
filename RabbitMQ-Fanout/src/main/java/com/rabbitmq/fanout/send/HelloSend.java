package com.rabbitmq.fanout.send;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HelloSend {

    @Autowired
    private AmqpTemplate template;


    public void send(){

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("delete", "11");

        String mapdata = JSON.toJSONString(paramsMap);


        //采用广播模式后
        //我们发送到路由器的消息会使得绑定到该路由器的每一个Queue接收到消息,这个时候就算指定了Key,或者规则，也不需要管

        template.convertAndSend("fanoutExchange","",mapdata);
    }

}
