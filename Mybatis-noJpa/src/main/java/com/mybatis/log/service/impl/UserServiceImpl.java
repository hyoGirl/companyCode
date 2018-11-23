package com.mybatis.log.service.impl;

import com.mybatis.log.dao.UserDao;
import com.mybatis.log.pojo.User;
import com.mybatis.log.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/8/2 0002 10:58
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Override
    public void deleteUserById(long id) {



        userDao.deleteUserById(id);

    }

    @Override
    public boolean addUser(User user) {


        return userDao.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUserByID(long id) {
        return userDao.deleteUserById(id);
    }

}
