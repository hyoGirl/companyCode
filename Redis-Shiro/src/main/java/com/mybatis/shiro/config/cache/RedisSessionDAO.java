package com.mybatis.shiro.config.cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 说明：重写Shiro对于session的CRUD，使用重写的Shiro的Cache接口，对session的CRUD在Redis中处理
 *
 * @author 徐磊
 * @time：2018年7月30日 下午4:43:04
 */

/**
 * redis实现共享session
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    // session 在redis过期时间是30分钟30*60
    private static int expireTime = 1800;

    private static String prefix = "weiyou-shiro-session:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void update(Session session) throws UnknownSessionException {
		 logger.debug("updateSession:{}", session.getId().toString());
		 String key = prefix + session.getId().toString();
	        if (!redisTemplate.hasKey(key)) {
	            redisTemplate.opsForValue().set(key, session);
	        }
	        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void delete(Session session) {
		logger.debug("delSession:{}", session.getId());
        redisTemplate.delete(prefix + session.getId().toString());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		logger.debug("activeSession");
        return Collections.emptySet();
	}

	@Override
	protected Serializable doCreate(Session session) {
		
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        logger.debug("createSession:{}", session.getId().toString());
        try {
//            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session);
            redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		 logger.debug("readSession:{}", sessionId.toString());
	        // 先从缓存中获取session，如果没有再去数据库中获取
	        Session session = null ;
	        try {
	            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
	        }catch (Exception e){
	            e.printStackTrace();
	            logger.error(e.getMessage(),e);
	        }
	        return session;
	}

}