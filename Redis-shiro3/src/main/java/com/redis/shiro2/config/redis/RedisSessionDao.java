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

@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {


    private Logger logger= LoggerFactory.getLogger(RedisSessionDao.class);


    private static String sessonPrefix="RR_";


//    private RedisTemplate<String,Object> redisTemplate;

    private static int expireTime = 1800;


    @Autowired
    RedisTemplate<String,Object>  redisTemplate;


    public RedisSessionDao(RedisTemplate<String,Object> redisTemplate){
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

        redisTemplate.opsForValue().set(sessonPrefix+sessions.toString(), session);
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
            sessions = (Session)redisTemplate.opsForValue().get(sessonPrefix+sessionId.toString());
            logger.info("doReadSession 读取到的session key为： "+sessonPrefix+sessionId.toString());

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

        String key = sessonPrefix + session.getId().toString();

        if(!redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().set(key, session);
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
        redisTemplate.delete(sessonPrefix + session.getId().toString());
    }

}
