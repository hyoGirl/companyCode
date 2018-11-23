package com.business.log.dao;


import com.business.log.pojo.User;

import java.util.List;

public interface UserDao {

    List<User> findAllUser();


    User findUserById(long id);


    boolean deleteUserById(long id);


    boolean addUser(User user);

    boolean updateUser(User user);
}
