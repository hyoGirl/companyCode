package com.spring.filter.controller;

import com.spring.filter.pojo.User;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Auther: Administrator
 * @Date: 2018/7/9 0009 14:39
 * @Description:
 */
@RestController
public class HelloController {


    @RequestMapping("login")
    public String login(String name,String pwd,HttpServletRequest request) {

        String aa = (String) request.getAttribute("aa");

        System.out.println(aa);


        HttpSession session = request.getSession();

        if(name.equals("root")&&pwd.equals("root")) {
            User user = new User();
            user.setName(name);
            session.setAttribute("user",user);
            return "登录成功";
        } else {
            return "用户名或密码错误!";
        }
    }
}
