package com.rabbitmq.java.message;

import com.rabbitmq.java.enums.ExchangeEnum;
import com.rabbitmq.java.enums.QueueEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Auther: Administrator
 * @Date: 2018/7/10 0010 16:07
 * @Description:
 */
public interface QueueMessageService extends RabbitTemplate.ConfirmCallback  {



    public void send(Object message, ExchangeEnum exchangeEnum, QueueEnum queueEnum) throws Exception;



}
