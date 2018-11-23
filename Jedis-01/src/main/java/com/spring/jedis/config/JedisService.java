package com.spring.jedis.config;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JedisService {

    //本次代码使用的是jedis2.9.0版本以上的


    @Autowired
    JedisPool jedisPool;

    public  Jedis getResource() {
//        System.out.println(jedisPool.getNumIdle());
        return jedisPool.getResource();
    }

    //=============================通用==========================================

    //指定缓存的失效时间
    public Long expire(String key, int time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long longTime = jedis.expire(key, time);
            return longTime;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {

            jedis.close();
        }
    }

    //获取Key的过期时间
    // 返回0代表了永久有效
    public long getExpire(String key) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    //判断key是否存在 对应的命令是 exists key
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }

    }

    // 根据正则表达式来验证key是否存在
    public boolean findKeysExist(String pattern) {

        Set<String> keys = keys(pattern);
        if (keys.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 删除多个对应的key
    public void delete(final String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    //删除对应的key
    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();

        }
    }


    //删除全部key
    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }


    // 删除key *  正则
    public void removePattern(final String pattern) {
        Set<String> keys = keys(pattern);
        if (keys.size() > 0) {
            for (String key : keys) {
                delete(key);
            }
        }
    }


    // 获取正则对应的key的集合
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }


    //=============================String==========================================


    // 设置KEY
    public String set(final String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String setValue = jedis.set(key, value);
            return setValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }

    //通过key来获取值
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return key == null ? null : jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }


    //将 key 中储存的数字值增一。
    //
    //如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
    public long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long incr = jedis.incr(key);

            return incr;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    //    将 key 所储存的值加上增量 increment 。
//
//    如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。负数就是递减
    public long incrBy(String key, long integer) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long incr = jedis.incrBy(key, integer);

            return incr;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }


    //=============================列表==========================================

    //redis的列表就是按照顺序把元素从左/头  右/尾 插入


    //向列表的头部添加数据，返回列表长度
    public Long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lpush = jedis.lpush(key, value);
            return lpush;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    //向列表尾部插入数据

    public Long rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            Long rpush = jedis.rpush(key, value);
            return rpush;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    //Redis Llen 命令用于返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。
    // 如果 key 不是列表类型，返回一个错误。
    public Long lLen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long llen = jedis.llen(key);
            return llen;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }

    }

    //返回列表 key 中，下标为 index 的元素。
    public String lIndex(String key, long index) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String lindex = jedis.lindex(key, index);
            return lindex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    //获取列表中的所有内容 list是有下标的，其实可以理解为获取0到-1的数据就是全部的了，
    //0 是第一位 -1 就是倒数第一位了
    public List<String> lRange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> lrange = jedis.lrange(key, start, end);
            return lrange;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }

    //lpop命令  从左边弹出列表中的第一个数据

    public String lPop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String lpop = jedis.lpop(key);
            return lpop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    //rpop 命令 从右边弹出最后的一个数据
    public String rPop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String rpop = jedis.rpop(key);
            return rpop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    // 将多个数据放入,执行 RPUSH 操作后，表的长度。

    public Long rPush(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }


    }

    //对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。

    public String lTrim(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String ltrim = jedis.ltrim(key, start, end);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    //安全的列表 返回被弹出的元素。
    //删除列表中的最后一个元素，将其追加到另一个列表。
    public String rPopLPush(String sourceKey, String targetKey) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String rpoplpush = jedis.rpoplpush(sourceKey, targetKey);
            return rpoplpush;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }


    //------------------------阻塞--------------------------------------

    // 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。

    // 如果是0 就代表了无限期
    public Object bLpop(int time, String keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> blpop = jedis.blpop(time, keys);
            return blpop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    public Object bRpop(int time, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> brpop = jedis.brpop(time, key);
            return brpop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();

        }
    }

    //仅当列表存在时，才会向列表中的头部添加一个值，返回列表的长度

    public Long lPushx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lpush = jedis.lpush(key, value);
            return lpush;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }

    }
    //仅当列表存在时，才会向列表中的尾部添加一个值，返回列表的长度

    public Long rPushX(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long rpush = jedis.rpush(key, value);
            return rpush;
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        } finally {
            jedis.close();
        }

    }

    //=============================散列==========================================

    //散列其实就是可以理解为一个map

    //往散列中放入一个值 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。

    /**
     * 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
     * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     * <p>
     * <p>
     * key 代表了键
     * item 代表了项
     * value 代表了 项对应的值
     **/
    public Long hset(String key, String item, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long hset = jedis.hset(key, item, value);
            return hset;
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        } finally {
            jedis.close();
        }

    }

    /**
     * 一次性批量插入数据到散列表中
     *
     * @param key
     * @param data
     * @return
     */
    public String hmset(String key, Map<String, String> data) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String hmset = jedis.hmset(key, data);
            return hmset;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    /**
     * 根据指定的键值以及项 来获取对应的value
     *
     * @param key  对应的键值
     * @param item 对应的项
     * @return
     */
    public String hget(String key, String item) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String hget = jedis.hget(key, item);
            return hget;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    /**
     * 根据指定的key值来获取对应的value
     *
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }

    /**
     * 根据指定的key以及项来删除对应的value
     *
     * @param key
     * @param item
     * @return
     */
    public long hdel(String key, String... item) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }


    /**
     * 判断这个散列表中是否存在这个值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hexists(String key, String item) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }


    /**
     * Hincrby 命令用于为哈希表中的字段值加上指定增量值。
     */
    public Long Hincrby(String key, String hashKey, final long delta) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key, hashKey, delta);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
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
    public long sAdd(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();

            return jedis.sadd(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据key获取Set中的所有值  如果set中只有一个值，也是获取这个set的值
     *
     * @param key 键
     * @return
     */
    public Set<String> sMembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sisMember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            jedis.close();
        }
    }

    //从集合中删除指定元素，返回删除成功的个数
    public Long sRem(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    // 判断这个set中是否存在这个成员
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
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
    public Long zAdd(String key, final double score, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {

            jedis.close();
        }
    }

    //获取有序列表中的元素个数
    public Long zCard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }


    /**
     * 查看某个成员的分数
     *
     * @param key
     * @param o
     * @return
     */
    public Double zScore(String key, String o) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(key, o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    /**
     * 增加成员分数　 zincrby key score member
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public Double zincrby(String key, final double score, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
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
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取有序集合中的后几位，默认是从小到大的排名
     *
     * @param key
     * @param start 下标从0开始
     * @param end
     * @return
     */
    public Set<String> zrange(String key, final long start, final long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
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
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取有序集合中的前几位,并且返回分数名称
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, final long start, final long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScoreWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }

    /**
     * ZINTERSTORE   计算给定的一个或多个有序集的交集.类似内连接
     *
     * 但是 结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
     *
     * 其中 给定 key 的数量必须以 numkeys 参数指定
     */

    /**
     *
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 保存到 key 的结果集的基数。
     */

    /**
     * key是要生成的新的默认集合   后面多个key是已经存在的集合
     *
     * @param dstkey
     * @param params 合并的集合数目
     * @param sets
     * @return
     */
    public Long zInterStore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            return jedis.zinterstore(dstkey, params, sets);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }

    }


    //=============================事务=======================================

    /**
     * Redis的事务是以multi命令开始。之后，用户输入多个命令，然后以exec命令结束，在exec命令还没有调用之前，用户不会进行任何实际操作
     * 当接受到multi命令后，redis会把从这个客户端接受到的命令全部都放入到一个队列里面，
     * 直到这个客户端发送exec命令，redis就会在不被打断的情况下，开始有序执行队列里面的命令。
     * 当输入MULTI命令后，服务器返回OK表示事务开始成功，然后依次输入需要在本次事务中执行的所有命令，
     * 每次输入一个命令服务器并不会马上执行，而是返回”QUEUED”，这表示命令已经被服务器接受并且暂时保存起来，最后输入EXEC命令后，
     * 本次事务中的所有命令才会被依次执行，可以看到最后服务器一次性返回了三个OK，这里返回的结果与发送的命令是按顺序一一对应的，
     * 这说明这次事务中的命令全都执行成功了。
     */

    //1： 开启事务  multi
    public Transaction multi() {
        Jedis jedis =jedisPool.getResource();
        Transaction transaction = jedis.multi();
        return transaction;
    }


    //2:  提交事务  exec
    //3:  回滚事务 回滚命令：discard（清除所有先前在一个事务中放入队列的命令，然后恢复正常的连接状态）
    //4:  添加监视：watch(key),监视某个key的变化，如果在事务执行之前这个(或这些) key被其他命令所改动，那么会取消事务里全部命令。


//  WATCH命令可以监控一个或多个键，一旦其中有一个键被修改（或删除），之后的事务就不会执行。监控一直持续到EXEC命令
// （事务中的命令是在EXEC之后才执行的，所以在MULTI命令后可以修改WATCH监控的键值）

    //5:  取消监视：unwatch(key)


    /**
     *
     事务的错误处理机制
     1、语法错误。命令不存在或参数错误的话，这在执行exec命令之前就知道了，因为如果正确加入到命令队列里会返回“QUEUED”，否则Redis将返回一个错误，
     如果Redis 2.6.5之前的版本会忽略错误的命令，执行其他正确的命令，2.6.5之后的版本会忽略这个事务中的所有命令，都不执行。
     2、运行错误。运行时发生的错误，即使错误发生（redis接收并执行，并没有把它当作错误来处理），后面的命令也会继续执行。
     */

    /*
    Redis保证一个事务中的所有命令要么都执行，要么都不执行。如果在发送EXEC命令前客户端断线了，则Redis会清空事务队列，事务中的所有命令都不会执行。
    而一旦客户端发送了EXEC命令，所有的命令就都会被执行，即使此后客户端断线也没关系，因为Redis中已经记录了所有要执行的命令。


    除此之外，Redis的事务还能保证一个事务内的命令依次执行而不被其他命令插入。试想客户端A需要执行几条命令，
    同时客户端B发送了一条命令，如果不使用事务，则客户端B的命令可能会插入到客户端A的几条命令中执行。如果不希望发生这种情况，也可以使用事务
     */


}
