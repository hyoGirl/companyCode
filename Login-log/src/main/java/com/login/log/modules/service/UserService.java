package com.login.log.modules.service;


import com.login.log.modules.model.User;

public interface UserService {


   User findUserByUserName(String username);

   User findUser(String name, String password);
}
