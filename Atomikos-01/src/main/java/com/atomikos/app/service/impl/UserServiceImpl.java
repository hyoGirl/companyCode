package com.atomikos.app.service.impl;

import com.atomikos.app.dao.user.UserMapper;
import com.atomikos.app.pojo.User;
import com.atomikos.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "userTransactionManager")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void delete(long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void insert(User user) {
        userMapper.addUser(user);
    }
}
