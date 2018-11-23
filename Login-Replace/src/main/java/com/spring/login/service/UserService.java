package com.spring.login.service;


import com.spring.login.pojo.User;

public interface UserService {


   User findUserByUserName(String username);

   User findUser(String name, String password);
}
