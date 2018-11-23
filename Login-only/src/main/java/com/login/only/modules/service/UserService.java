package com.login.only.modules.service;

import com.login.only.modules.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {


   User findUserByUserName(String username);

   User findUser(String name,String password);
}
