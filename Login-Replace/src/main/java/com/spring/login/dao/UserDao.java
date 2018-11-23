package com.spring.login.dao;


import com.spring.login.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {


   User findUserByName(String name);
   User findUser(@Param("name") String name, @Param("password") String password);
}
