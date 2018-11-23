package com.rabbitmq.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendConfig {


    @Bean
    public FanoutExchange fanoutExchange(){

        //定义广播交换机
        return new FanoutExchange("fanoutExchange");
    }

    //定义三个不同的队列
    @Bean
    public Queue AMessage(){

        return new Queue("Fanout.A");
    }

    @Bean
    public Queue BMessage(){

        return new Queue("Fanout.B");
    }

    @Bean
    public Queue CMessage(){

        return new Queue("Fanout.C");
    }


    //分别将队列绑定到指定的交换机上，发送端通过发送消息到交换机上，然后再根据路由来分发
    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }



}
