package com.atomikos.four.service.impl;


import com.atomikos.four.entity.User;
import com.atomikos.four.mapper.master.BookMapper;
import com.atomikos.four.mapper.slave.UserMapper;
import com.atomikos.four.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserMapper userMapper;


    @Override
//    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public void delete(long id)  {

        userMapper.deleteUserById(id);

        int i=100/0;


//        try {
//            int i=100/0;
//        } catch (Exception e) {
//            throw new Exception("我的错，用户删除失败！！！！");
//        }

    }

    @Override
    public void insert(User user) {
        userMapper.addUser(user);
    }
}
