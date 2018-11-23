package com.rabbitmq.topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Administrator
 * @Date: 2018/7/13 0013 10:42
 * @Description:
 */
@Configuration
public class SenderConf {


    //定义第一个队列
    @Bean(name="message")
    public Queue queueMessage() {
        return new Queue("topic.msg");
    }

    //定义第二个队列
    @Bean(name="messages")
    public Queue queueMessages() {
        //

        return new Queue("topic.msgs");
    }

    //定义交换机
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("topic1exchange");
    }


    //通过路由key将交换机绑定到第一个队列上
    @Bean
    Binding bindingExchangeForMessage (@Qualifier("message") Queue queueMessage, TopicExchange exchange){

        //这个标识的是可以接受的路由key只能是topic.msg

        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.msg");
    }


    //将交换机绑定到第二个队列上
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages, TopicExchange exchange) {

        //*表示一个词,#表示零个或多个词。这个标识可以接受的路由key是topic。xxx

        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");

    }

}

