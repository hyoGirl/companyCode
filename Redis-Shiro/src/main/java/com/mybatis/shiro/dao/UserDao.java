package com.mybatis.shiro.dao;

import org.apache.ibatis.annotations.Param;

import com.mybatis.shiro.pojo.User;

public interface UserDao {

	
	User findUserByUsername(@Param("name")String name);

	
	
}
