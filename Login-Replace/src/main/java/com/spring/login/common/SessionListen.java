package com.spring.login.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListen implements HttpSessionListener {


    private Logger logger= LoggerFactory.getLogger(SessionListen.class);


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {


        logger.info("有新的会话开始了 "+httpSessionEvent.getSession().getId());

        httpSessionEvent.getSession().setAttribute("forceLoginOut","no");

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {



    }
}
