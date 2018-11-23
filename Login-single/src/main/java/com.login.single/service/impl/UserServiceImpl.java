package com.login.single.service.impl;

import com.login.single.model.LoginUser;
import com.login.single.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private int count=0;

    @Override
    public LoginUser findUser(String userName, String password) {

        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(userName);
        loginUser.setPassword(password);
        loginUser.setId(++count);
        return loginUser;
    }
}
