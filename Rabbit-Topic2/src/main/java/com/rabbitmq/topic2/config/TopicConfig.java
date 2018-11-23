package com.rabbitmq.topic2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {


    /*
    durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失; durable=false,相反
    auto-delete=true:无消费者时，队列自动删除; auto-delete=false：无消费者时，队列不会自动删除
    排他性，exclusive=true:首次申明的connection连接下可见; exclusive=false：所有connection连接下
    */


    @Bean(name = "queue")
    public Queue queue(){

        return new Queue("topic2.first",true,false,false,null);
    }


    //定义第二个队列
    @Bean(name="secondQueue")
    public Queue secondQueue() {

        return new Queue("topic2.second",true,false,false,null);
    }


    //定义交换机
    @Bean
    public TopicExchange exchange() {

        return new TopicExchange("topic2Exchange");
    }


    //通过路由key将交换机绑定到第一个队列上
    @Bean
    Binding bindingExchangeForMessage ( Queue queue, TopicExchange exchange){

        //这个标识的是可以接受的路由key只能是topic.first

        return BindingBuilder.bind(queue).to(exchange).with("topic2.first");
    }


    //将交换机绑定到第二个队列上
    @Bean
    Binding bindingExchangeMessages( Queue secondQueue, TopicExchange exchange) {

        //*表示一个词,#表示零个或多个词。这个标识可以接受的路由key是topic。xxx

        return BindingBuilder.bind(secondQueue).to(exchange).with("topic2.#");
//        return BindingBuilder.bind(secondQueue).to(exchange).with("topic.second");

    }



}
