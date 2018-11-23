package com.spring.login.controller;


import com.spring.login.common.LoginUser;
import com.spring.login.pojo.User;
import com.spring.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    UserService userService;


    Map loginMap=new HashMap();

    @RequestMapping("/login")
    public String index(ModelMap map){
        return "login";
    }

    @RequestMapping("/user/login")
    public String doLogin(String username,String password,Model model){

        User userByUserName = userService.findUserByUserName(username);
        if(userByUserName!=null){
            String pwd = userByUserName.getPassword();
            if(pwd.equals(password)){
                model.addAttribute("name",username);
                return "hello";

            }
        }

        return "login";
    }

    @RequestMapping("/user/login2")
    public String doLogin2(String username, String password, Model model){

        Map<String,Object> userMap= LoginUser.loginMap;
//        Map<String,Object> userMap=loginMap;

        User user = userService.findUser(username, password);

        if(user!=null){
            for(Map.Entry entry: userMap.entrySet()){
                if(entry.getKey().equals(username)){
                    model.addAttribute("msg",username+":已经登录过了");
                    return "login";
                }
            }
            userMap.put(username,user);
            model.addAttribute("name",username);
            return "hello";
        }
        return "login";
    }




}
