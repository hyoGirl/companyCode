package com.redis.learn.simple.queue;

import com.redis.learn.simple.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisProduce {


    @Autowired
    RedisService redisService;

    public void produce(){


        for(int i=0;i<10;i++){
            System.out.println("正在从头部插入新的数据"+"value_" + i);
            redisService.lpush("listingList","value_" + i);
        }
    }


}
