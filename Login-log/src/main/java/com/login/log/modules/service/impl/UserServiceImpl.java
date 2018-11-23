package com.login.log.modules.service.impl;

import com.login.log.modules.dao.UserDao;
import com.login.log.modules.model.User;
import com.login.log.modules.service.UserService;
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
