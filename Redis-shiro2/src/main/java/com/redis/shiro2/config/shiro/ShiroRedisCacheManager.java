package com.redis.shiro2.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *  实现cacheManager类，这里用了redis作为cache的存储
 *
 */
public class ShiroRedisCacheManager implements CacheManager {


    private Logger logger= LoggerFactory.getLogger(ShiroRedisCacheManager.class);


    private RedisTemplate<byte[],byte[]>  redisTemplate;

    public ShiroRedisCacheManager(RedisTemplate redisTemplate){

        this.redisTemplate = redisTemplate;
    }


    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        logger.info("获取名称为: " + name + " 的RedisCache实例");


        return new ShiroRedisCache(redisTemplate,name);
    }
}
