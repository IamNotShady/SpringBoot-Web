package com.boot.shiro.util;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.boot.util.ShortUuid;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author zxx
 * @Description
 * @Date Created on 2017/11/11
 */
public class RedisCacheSessionDao extends CachingSessionDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String RCE_SESSION_KEY_PREFIX = "rce_session_";

    @Value("${global.session.expire}")
    private long globalSessionTimeOut;

    private final long globalTimeOut;

    @Resource(name="sessionRedis")
    private RedisTemplate<Serializable, Session> redisTemplate;

    public RedisCacheSessionDao() {
        if (globalSessionTimeOut == 0) {
                globalSessionTimeOut = 604800000L;
        }
        globalTimeOut = globalSessionTimeOut + 1000 * 60 * 60 * 3;
        logger.info("REDIS GLOBAL SESSION REAL TIMEOUT: {}", globalTimeOut);
    }

    @Override
    protected void doUpdate(Session session) {
        String sessionKey = RCE_SESSION_KEY_PREFIX + session.getId();
        redisTemplate.opsForValue().set(sessionKey, session);
        redisTemplate.expire(sessionKey, globalTimeOut, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        redisTemplate.delete(RCE_SESSION_KEY_PREFIX + session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        String sessionKey = RCE_SESSION_KEY_PREFIX + sessionId;
        redisTemplate.opsForValue().set(sessionKey, session);
        redisTemplate.expire(sessionKey, globalTimeOut, TimeUnit.MILLISECONDS);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisTemplate.opsForValue().get(RCE_SESSION_KEY_PREFIX + sessionId);
    }

    /**
     * 自定义sessionID
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable generateSessionId(Session session) {
        return new ShortUuid.Builder().build().toString();
    }
}

