package com.login.log.modules.dao;


import com.login.log.modules.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {


   User findUserByName(String name);
   User findUser(@Param("name") String name, @Param("password") String password);
}
