package com.login.single.web;

import com.login.single.model.LoginUser;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListen implements HttpSessionListener {


    public static int number = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        number++;

        System.err.println("有新的session 开始了，当前session的大小为： "+number);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {



        HttpSession session = httpSessionEvent.getSession();

//        ServletContext servletContext = session.getServletContext();
//
//
//        List onlineUsers = (List) servletContext.getAttribute("onlineUsers");

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

//        onlineUsers.remove(session);
//
//        System.out.println(onlineUsers.size());


        number--;

        System.err.println("销毁了session,内容为：   "+loginUser.toString());


        System.err.println("当前session的大小为：   "+number);



    }
}
