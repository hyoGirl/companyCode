package com.spring.login.service.impl;


import com.spring.login.dao.UserDao;
import com.spring.login.pojo.User;
import com.spring.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    @Override
    public User findUserByUserName(String username) {

        return userDao.findUserByName(username);
    }

    @Override
    public User findUser(String name, String password) {
        return userDao.findUser(name,password);
    }
}
