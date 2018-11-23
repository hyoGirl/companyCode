package com.mybatis.shiro.config.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 说明：实现缓存共享
 * 
 * @param <K>
 * @param <V>
 * @author 徐磊
 * @time：2018年7月30日 下午4:38:04
 */
// Shiro的缓存类
@Component
public class ShiroCache<K, V> implements Cache<K, V> {

	private static final String REDIS_SHIRO_CACHE = "weiyou-shiro-cache:";
	private String cacheKey;
	private RedisTemplate<K, V> redisTemplate;
	private long globExpire = 30;
	

	public ShiroCache() {
		super();
	}

    public ShiroCache(String name, RedisTemplate client) {

        this.cacheKey = REDIS_SHIRO_CACHE + name + ":";
        this.redisTemplate = client;
    }

	@Override
	public V get(K key) throws CacheException {
		
		System.out.println("--------------正在从redis中获取信息---------------");
		redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
		return redisTemplate.boundValueOps(getCacheKey(key)).get();
	}

	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		redisTemplate.boundValueOps(getCacheKey(key)).set(value);
		return old;
	}

	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(keys());
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Set<K> keys() {
		return redisTemplate.keys(getCacheKey("*"));
	}

	@Override
	public Collection<V> values() {
		Set<K> set = keys();
		List<V> list = new ArrayList<>();
		for (K s : set) {
			list.add(get(s));
		}
		return list;
	}

	private K getCacheKey(Object k) {
		return (K) (this.cacheKey + k);
	}
}
