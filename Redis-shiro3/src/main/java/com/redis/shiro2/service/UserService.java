package com.redis.shiro2.service;


import com.redis.shiro2.dao.UserDao;
import com.redis.shiro2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	
	public User findUserByUserName(String name) {
		return userDao.findUserByUsername(name);
		
	}

}
