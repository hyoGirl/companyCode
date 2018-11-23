package com.redis.shiro2.dao;


import com.redis.shiro2.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

	
	User findUserByUsername(@Param("name") String name);

	
	
}
