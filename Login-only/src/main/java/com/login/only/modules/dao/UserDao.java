package com.login.only.modules.dao;

import com.login.only.modules.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {


   User findUserByName(String name);
   User findUser(@Param("name") String name, @Param("password") String password);
}
