package com.simple.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {


    @Bean
    public Queue topicAQueue(){
        return new Queue("simpleTopic.A");
    }


    @Bean
    public Queue topicBQueue(){
        return new Queue("simpleTopic.B");
    }



    //定义交换机
    @Bean
    public TopicExchange exchange() {

        return new TopicExchange("simpleTopicExchange");
    }


    @Bean
    Binding bindingExchangeForMessage ( Queue topicAQueue, TopicExchange exchange){


        return BindingBuilder.bind(topicAQueue).to(exchange).with("simpleTopic.A");
    }


    //将交换机绑定到第二个队列上
    @Bean
    Binding bindingExchangeMessages( Queue topicBQueue, TopicExchange exchange) {


       return BindingBuilder.bind(topicBQueue).to(exchange).with("simpleTopic.#");

    }
}
