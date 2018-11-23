package com.mybatis.shiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	@Value("${spring.redis.database}")
	private int database;
//	@Value("${spring.redis.password}")
//	private String password;

	/** * 连接redis的工厂类 * @return */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(host);
		factory.setPort(port);
		factory.setTimeout(timeout);
//		factory.setPassword(password);
		factory.setDatabase(database);
		return factory;
	}
	
	/**
	 * 
	 * 说明：序列化
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {

		RedisTemplate<String, Object> template = new RedisTemplate<>();

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		  //设置序列化Key的实例化对象
		template.setKeySerializer(stringRedisSerializer);
		
		template.setHashKeySerializer(stringRedisSerializer);
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		template.setDefaultSerializer(jackson2JsonRedisSerializer);
		
		
		template.setConnectionFactory(jedisConnectionFactory());

		template.afterPropertiesSet();

		return template;
	}
	
	
	 /**
     * 采用RedisCacheManager作为缓存管理器
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        return new RedisCacheManager(redisTemplate);
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        // 多个缓存的名称,目前只定义了一个
       // rcm.setCacheNames(Arrays.asList("test"));
        //设置缓存过期时间(秒)
        rcm.setDefaultExpiration(1800);
        
        return rcm;
        
    }
    
    /**
     * 自定义生成key的规则
     * @return
     */
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                //格式化缓存key字符串
//                StringBuilder sb = new StringBuilder();
//                //追加类名
//                sb.append(o.getClass().getName());
//                //追加方法名
//                sb.append(method.getName());
//                //遍历参数并且追加
//                for (Object obj : objects) {
//                    sb.append(obj.toString());
//                }
//                System.out.println("调用Redis缓存Key : " + sb.toString());
//                return sb.toString();
//            }
//        };
//    }
}
