package com.spring.jedis.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@EnableAutoConfiguration
//@ConfigurationProperties(prefix = "spring.redis")
public class JedisConfig {

    private static Logger logger = LoggerFactory.getLogger(JedisConfig.class);



    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;


    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //设置最大的空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);

        //获取Jedis连接的最大等待时间（）
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        //设置最大连接数（200个足够用了，没必要设置太大）
        jedisPoolConfig.setMaxTotal(maxActive);

        //
        jedisPoolConfig.setMinIdle(minIdle);

        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout,null);

        logger.info("JedisPool注入成功！");

        logger.info("redis地址：" + host + ":" + port);

        return  jedisPool;
    }




}
