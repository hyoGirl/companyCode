package com.mybatis.log.dao;

import com.mybatis.log.pojo.User;

import java.util.List;


public interface UserDao {

    List<User> findAllUser();


    User findUserById(long id);


    boolean deleteUserById(long id);


    boolean addUser(User user);

    boolean updateUser(User user);
}
