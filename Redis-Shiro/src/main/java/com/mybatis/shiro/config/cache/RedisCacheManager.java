package com.mybatis.shiro.config.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * 说明：redis实现shiro的缓存管理器，把RedisTemplate传入到shiro里面
 *   从而实现shiro里面的缓存都由redis来控制
 * 
 * @author 徐磊
 * @time：2018年7月30日 下午4:46:56
 */
@Component
public class RedisCacheManager implements CacheManager {
	
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	

	public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}




	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		
		System.out.println("将RedisTemplate注入到shiro里面");
		
		//核心代码
		return new ShiroCache<K, V>(name, redisTemplate);
	}
}
