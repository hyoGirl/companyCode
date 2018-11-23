package com.redis.learn.simple.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 参考文章:  https://blog.csdn.net/plei_yue/article/details/79362372
 */
@Service
public class RedisService {


    @Autowired
    RedisTemplate redisTemplate;


    //=============================通用==========================================

    //指定缓存的失效时间
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //获取Key的过期时间
    // 返回0代表了永久有效
    public long getExpire(String key) {

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    //判断key是否存在 对应的命令是 exists key
    public boolean exists(String key) {


        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //删除对应的key
    public void delete(String key) {

        redisTemplate.delete(key);
    }

    //=============================String==========================================






    // 设置KEY
    public boolean set(final String key, Object value) {

        boolean result = false;
        try {
            ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //通过key来获取值
    public Object get(String key) {

        return key == null ? null : redisTemplate.opsForValue().get(key);

    }


    //设置Key的时候，同时还设置好过期时间
    public boolean expireKey(String key, Object value, Long time) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value, time);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //incr  将 key 中储存的数字值增一。
    //
    //如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。




    //进行递增.递减使用负数就可以了
    public long incrBy(String key, long value) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long increment = valueOperations.increment(key, value);
        return increment;
    }

    public Double incrBy(String key, Double value) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Double increment = valueOperations.increment(key, value);
        return increment;
    }


    //=============================列表==========================================
//    Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）

    //把list放入缓存中 对应的命令是LPUSH左边插入 也就是头部。这样最后一个的下标是0

    //Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)  这个list其实可以简单理解为无锁队列


    //向列表的头部添加数据，返回列表长度
    public Long lpush(String key, Object value) {

        Long length = redisTemplate.opsForList().leftPush(key, value);

        return length;
    }

    public Long rpush(String key, Object value) {

        Long length = redisTemplate.opsForList().rightPush(key, value);

        return length;
    }


    //Redis Llen 命令用于返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。 如果 key 不是列表类型，返回一个错误。
    public Long lLen(String key) {

        Long size = redisTemplate.opsForList().size(key);

        return size;

    }

    //获取列表中的所有内容 list是有下标的，其实可以理解为获取0到-1的数据就是全部的了，
    //0 是第一位 -1 就是倒数第一位了
    public List<Object> lRange(String key, long start, long end) {

        List list = redisTemplate.opsForList().range(key, start, end);

        return list;

    }

    //lpop命令  从左边弹出列表中的第一个数据

    public Object lPop(String key) {

        Object value = redisTemplate.opsForList().leftPop(key);

        return value;
    }

    //rpop 命令 从右边弹出最后的一个数据

    public Object rPop(String key) {

        Object value = redisTemplate.opsForList().rightPop(key);

        return value;
    }

    // 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。

    public Object bLpop(int time, String keys) {

        Object value = redisTemplate.opsForList().leftPop(keys, time, TimeUnit.SECONDS);

        return value;
    }

    public Object bRpop(int time, String keys) {

        Object value = redisTemplate.opsForList().rightPop(keys, time, TimeUnit.SECONDS);

        return value;
    }

    //仅当列表存在时，才会向列表中的头部添加一个值，返回列表的长度

    public Long lPushx(String key, Object value) {

        Long length = redisTemplate.opsForList().leftPushIfPresent(key, value);

        return length;

    }
    //仅当列表存在时，才会向列表中的尾部添加一个值，返回列表的长度

    public Long rPushX(String key, Object value) {

        Long length = redisTemplate.opsForList().rightPushIfPresent(key, value);

        return length;

    }


    // 将多个数据放入

    public boolean rPush(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。

    public void lTrim(String key, long start, long end) {

        redisTemplate.opsForList().trim(key, start, end);
    }


    //安全的列表
    //删除列表中的最后一个元素，将其追加到另一个列表。
    public Object rPopLPush(String sourceKey, String targetKey) {


        Object value = redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, targetKey);
        return value;

    }


    //


    //=============================散列==========================================

    //散列其实就是可以理解为一个map

    //往散列中放入一个值 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。

    /**
     * 单独插入一个数据
     * <p>
     * key 代表了键
     * item 代表了项
     * value 代表了 项对应的值
     **/
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 一次性批量插入数据到散列表中
     *
     * @param key
     * @param data
     * @return
     */
    public boolean hmset(String key, Map<String, String> data) {

        try {
            redisTemplate.opsForHash().putAll(key, data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }


    /**
     * 根据指定的键值以及项 来获取对应的value
     *
     * @param key  对应的键值
     * @param item 对应的项
     * @return
     */
    public Object hget(String key, String item) {

        return redisTemplate.opsForHash().get(key, item);
    }


    /**
     * 根据指定的key值来获取对应的value
     *
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(String key) {

        return redisTemplate.opsForHash().entries(key);

    }

    /**
     * 根据指定的key以及项来删除对应的value
     *
     * @param key
     * @param item
     * @return
     */
    public long hdel(String key, Object... item) {

        Long delete = redisTemplate.opsForHash().delete(key, item);

        return delete;
    }


    /**
     * 判断这个散列表中是否存在这个值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {

        return redisTemplate.opsForHash().hasKey(key, item);
    }


    /**
     * Hincrby 命令用于为哈希表中的字段值加上指定增量值。
     */
    public Long Hincrby(Object key, String hashKey, final long delta) {
        Long increment = redisTemplate.opsForHash().increment(key, hashKey, delta);
        return increment;
    }

    public Double Hincrby(Object key, String hashKey, final Double delta) {
        Double increment = redisTemplate.opsForHash().increment(key, hashKey, delta);
        return increment;
    }


    //=============================集合==========================================

    //往set中添加元素，返回的是本次添加成功的元素数量。.key是set这个集合的名称，value是传入到里面的值

    /**
     * 将数据放入set缓存 返回的是本次添加成功的元素数量
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取Set中的所有值  如果set中只有一个值，也是获取这个set的值
     *
     * @param key 键
     * @return
     */
    public Set<String> sMembers(Object key) {

        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sisMember(Object key, Object value) {

        return redisTemplate.opsForSet().isMember(key, value);
    }

    //从集合中删除指定元素，返回删除成功的个数
    public Long sRem(Object key, Object... value) {
        return redisTemplate.opsForSet().remove(key, value);
    }


    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //=============================有序集合=======================================

    //有序集合里面保存的元素都是不重复的，同时还为里面每一个元素都设置了浮点类型的分数，根据这个得分来进行排序

    /**
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return
     */
    public Boolean zAdd(Object key, Object value, final double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    //获取有序列表中的元素个数
    public Long zCard(Object key) {
        return redisTemplate.opsForZSet().zCard(key);
    }


    /**
     * 查看某个成员的分数
     *
     * @param key
     * @param o
     * @return
     */
    public Double zScore(Object key, Object o) {

        Double score = redisTemplate.opsForZSet().score(key, o);

        return score;
    }


    /**
     * 增加成员分数　 zincrby key score member
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public Double zincrby(Object key, String value, final double delta) {


        Double aDouble = redisTemplate.opsForZSet().incrementScore(key, value, delta);

        return aDouble;
    }


    /**
     * 返回列表中分数大于A小于B
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return
     */
    public Set<String> zrangebyscore(String key, final double min, final double max) {
        Set set = redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);

        return set;
    }

    /**
     * 获取有序集合中的后几位，默认是从小到大的排名
     *
     * @param key
     * @param start 下标从0开始
     * @param end
     * @return
     */
    public Set<String> range(String key, final long start, final long end) {

        Set range = redisTemplate.opsForZSet().range(key, start, end);
        return range;
    }

    /**
     * 获取有序集合中的前几位
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, final long start, final long end) {

        Set<String> range = redisTemplate.opsForZSet().reverseRange(key, start, end);
        return range;
    }

    /**
     * 获取有序集合中的前几位,并且返回分数名称
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRevRangeWithScores(String key, final long start, final long end) {

        Set<String> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        return set;
    }

    /**
     * ZINTERSTORE   计算给定的一个或多个有序集的交集.类似内连接
     *
     * 但是 结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
     *
     * 其中 给定 key 的数量必须以 numkeys 参数指定
     */

    /**
     * key是要生成的新的默认集合   后面两个key是已经存在的集合
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return  保存到 key 的结果集的基数。
     */
    public Long zInterStore(String key, String otherKey, String destKey){

        Long aLong = redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);

        return aLong;

    }

    //=============================事务=======================================

    /**
     * 当输入MULTI命令后，服务器返回OK表示事务开始成功，然后依次输入需要在本次事务中执行的所有命令，
     *
     * 每次输入一个命令服务器并不会马上执行，而是返回”QUEUED”，这表示命令已经被服务器接受并且暂时保存起来，最后输入EXEC命令后，
     *
     *
     * 本次事务中的所有命令才会被依次执行，可以看到最后服务器一次性返回了三个OK，这里返回的结果与发送的命令是按顺序一一对应的，
     *
     *
     * 这说明这次事务中的命令全都执行成功了。
     */

    //1：开启事务
    public void multi(){
        redisTemplate.multi();
    }


    //2: 提交事务
    public List<Object> exec(){
        List exec = redisTemplate.exec();
        return exec;
    }


    //3: 回滚事务 回滚命令：discard（清除所有先前在一个事务中放入队列的命令，然后恢复正常的连接状态）
    public void discard(){
        redisTemplate.discard();
    }


    //4: 添加监视：watch(key),监视某个key的变化，如果在事务执行之前这个(或这些) key被其他命令所改动，那么会取消事务里全部命令。
    public void watch(Object key) {

        redisTemplate.watch(key);
    }

    //5:取消监视：unwatch(key)
    public  void  unwatch(){
        redisTemplate.unwatch();
    }


    /**
     *
         事务的错误处理机制
         1、语法错误。命令不存在或参数错误的话，这在执行exec命令之前就知道了，因为如果正确加入到命令队列里会返回“QUEUED”，否则Redis将返回一个错误，
         如果Redis 2.6.5之前的版本会忽略错误的命令，执行其他正确的命令，2.6.5之后的版本会忽略这个事务中的所有命令，都不执行。
         2、运行错误。运行时发生的错误，即使错误发生（redis接收并执行，并没有把它当作错误来处理），后面的命令也会继续执行。
     */


}
