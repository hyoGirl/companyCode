package com.redis.shiro2.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class ShiroRedisCacheManager implements CacheManager {


    private RedisTemplate<String,Object>  redisTemplate;

    public ShiroRedisCacheManager(RedisTemplate redisTemplate){

        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        return new ShiroRedisCache(redisTemplate,name);
    }
}
