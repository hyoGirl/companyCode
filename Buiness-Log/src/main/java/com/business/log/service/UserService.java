package com.business.log.service;


import com.business.log.pojo.User;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/8/2 0002 10:58
 * @Description:
 */
public interface UserService {


    List<User> findAllUser();


    User findUserById(long id);


    void deleteUserById(long id);

    boolean addUser(User user);


    boolean updateUser(User user);

    boolean deleteUserByID(long id);
}
