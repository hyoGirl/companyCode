package com.redis.shiro2.config.shiro;

import com.redis.shiro2.util.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @param <K>
 * @param <V>
 */
public class ShiroRedisCache  <K, V> implements Cache<K, V> {


    private Logger logger= LoggerFactory.getLogger(ShiroRedisCache.class);

    private static  String prefix="REDIS_cache";

    private String cacheKey;

    private RedisTemplate redisTemplate;

    public ShiroRedisCache(RedisTemplate redisTemplate){

        this.redisTemplate = redisTemplate;
    }

    public  ShiroRedisCache(RedisTemplate redisTemplate,String name){

        this.redisTemplate = redisTemplate;
        this.cacheKey=prefix+name;

    }



    @Override
    public V get(K k) throws CacheException {

        if (k == null) {
            return null;
        }

        logger.info("-----从redis中获取信息,k为："+k);

        return (V) redisTemplate.opsForValue().get(getBytesKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {


        if (k== null || v == null) {
            return null;
        }

        logger.info("+++++往redis中放置信息,k为："+k);


        redisTemplate.opsForValue().set(getBytesKey(k),v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {

        Object obj = redisTemplate.opsForValue().get(getBytesKey(k));



//        V v = (V)redisTemplate.opsForValue().get(JSON.toJSONString(k));

        logger.info("-----往redis中移除信息,k为："+k);

        redisTemplate.delete(getBytesKey(k));

        V v = (V)obj;

        return v;
    }

    @Override
    public void clear() throws CacheException {


        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public int size() {

        return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
    }

    @Override
    public Set<K> keys() {


        Set keys = redisTemplate.keys(prefix+"*");

        return keys;
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


    private byte[] getBytesKey(K key){
        if(key instanceof String){
            String prekey = this.prefix + key;
            return prekey.getBytes();
        }else {
            return SerializeUtil.serialize(key);
        }
    }
}
