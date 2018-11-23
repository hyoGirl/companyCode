package com.redis.shiro2.config.redis;

import com.redis.shiro2.util.SerializeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {




    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database}")
    private int database;
	@Value("${spring.redis.password}")
	private String password;

    /** * 连接redis的工厂类 * @return */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
		factory.setPassword(password);
        factory.setDatabase(database);
        return factory;
    }


   @Bean
    public CacheManager redisCacheManager(RedisTemplate<String,Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        cacheManager.setDefaultExpiration(1800);

        return cacheManager;

    }




    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory) {

        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setHashValueSerializer(new SerializeUtils());

        redisTemplate.setValueSerializer(new SerializeUtils());

        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }


}
