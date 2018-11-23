package com.login.only.modules.controller;

import com.login.only.common.LoginUser;
import com.login.only.common.aop.LoggerAop;
import com.login.only.modules.model.User;
import com.login.only.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @PostMapping("/user/test")
    @ResponseBody
    public String doLogin(@RequestBody User user){
/*
        User userByUserName = userService.findUserByUserName(username);
        if(userByUserName!=null){
            String pwd = userByUserName.getPassword();
            if(pwd.equals(password)){
                model.addAttribute("name",username);
                return "hello";

            }
        }

        return "login";*/


        System.out.println(user);

        return "ceshi";

    }

    @PostMapping("/user/login3")
    @ResponseBody
    public String doLogin3(User user){


        System.out.println(user);

        return "ceshi";

    }

    @RequestMapping("/user/login2")
    public String doLogin3(String userName, String password, Model model){

        Map<String,Object> userMap= LoginUser.loginMap;

        User user = userService.findUser(userName, password);

        if(user!=null){
            for(Map.Entry entry: userMap.entrySet()){
                if(entry.getKey().equals(userName)){
                    model.addAttribute("msg",userName+":已经登录过了");
                    return "login";
                }
            }
            userMap.put(userName,user);
            model.addAttribute("name",userName);
            return "hello";
        }
        return "login";
    }




}
