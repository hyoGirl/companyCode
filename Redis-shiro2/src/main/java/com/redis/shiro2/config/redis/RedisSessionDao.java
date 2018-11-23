package com.redis.shiro2.config.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.TimeUnit;


/**
 *
 * 自己实现的 Session 的存储类。用redis来实现对session的创建以及读取操作
 *
 *
 */
@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {


    private Logger logger= LoggerFactory.getLogger(RedisSessionDao.class);


    private static String sessonPrefix="RR_";
//    private RedisTemplate<String,Object> redisTemplate;

    private static int expireTime = 1800;


    @Autowired
    RedisTemplate<byte[],byte[]>  redisTemplate;


    public RedisSessionDao(RedisTemplate<byte[],byte[]> redisTemplate){
        this.redisTemplate = redisTemplate;

    }

    public RedisSessionDao() {
        super();
    }

    /**
     * 创建session 保存到redis中
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessions = super.doCreate(session);
        this.assignSessionId(session, sessions);
        logger.info("doCreate 保存的session key为： "+sessions.toString());

        redisTemplate.opsForValue().set(sessions.toString().getBytes(), sessionToByte(session));
        return sessions;
    }

    /**
     * 获取session,如果没有，就从redis中获取
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {

        Session sessions = super.doReadSession(sessionId);
        if(sessions==null){
            byte[] bytes =  redisTemplate.opsForValue().get(sessionId.toString().getBytes());

            logger.info("doReadSession 读取到的session key为： "+sessionId.toString());

            if(bytes != null && bytes.length > 0){
                sessions = byteToSession(bytes);
            }
        }
        return sessions;
    }

    /**
     *  记录session的最后时间
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {

        logger.info("doUpdate 记录session的最后时间 "+session);

        super.doUpdate(session);

        byte[] key = session.getId().toString().getBytes();

        if(!redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().set(session.getId().toString().getBytes(),sessionToByte(session));
        }
        redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
    }

    /**
     * 删除session
     * @param session
     */
    @Override
    protected void doDelete(Session session) {

        logger.info("doDelete 开始删除session "+ session);
        super.doDelete(session);
        redisTemplate.delete(session.getId().toString().getBytes());
    }



    private byte[] sessionToByte(Session session){
        if (null == session){
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        ObjectOutputStream oo ;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;

    }
    private Session byteToSession(byte[] bytes){
        if(0==bytes.length){
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return session;
    }
}
