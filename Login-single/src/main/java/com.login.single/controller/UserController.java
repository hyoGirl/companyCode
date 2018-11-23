package com.login.single.controller;


import com.login.single.model.LoginUser;
import com.login.single.service.UserService;
import com.login.single.util.LoginManagerService;
import com.login.single.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class UserController {



    @Autowired
    UserService userService;



    @Autowired
    LoginManagerService loginManagerService;


    @GetMapping("/login")
    public String tologin(){

        return "login";
    }

    @GetMapping("/test")
    public String toTest(){

        return "test";
    }








    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model){

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");


        System.out.println(userName+" : "+password);
        LoginUser user = userService.findUser(userName, password);


        model.addAttribute("name",userName);

        //使用过滤器来进行校验重复，有可能存在多个账号

        HttpSession currentSession = request.getSession();

        currentSession.setAttribute("loginUser",user);


        Map<String, String> sessionMap = SessionManager.getSessionMap();


        if(!sessionMap.containsKey(user.getUserName())){
            sessionMap.put(user.getUserName(),currentSession.getId());
            System.out.println(user.getUserName()+" 第一次登陆时的session ID是：" + currentSession.getId());

        }else{
            //同名用户上一次登录的时候
            String beforeSessionID = sessionMap.get(user.getUserName());
            if(sessionMap.containsKey(user.getUserName())  && !beforeSessionID.equals(currentSession.getId()) ){
                sessionMap.remove(user.getUserName());
                sessionMap.put(user.getUserName(),currentSession.getId());
          }
        }
        return "hello";
    }



}




