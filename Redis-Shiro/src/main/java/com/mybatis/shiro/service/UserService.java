package com.mybatis.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.shiro.dao.UserDao;
import com.mybatis.shiro.pojo.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	
	public User findUserByUserName(String name) {
		return userDao.findUserByUsername(name);
		
	}

}
