package com.spring.shiro.service.impl;

import com.spring.shiro.dao.UserInfoDao;
import com.spring.shiro.pojo.UserInfo;
import com.spring.shiro.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Administrator
 * @Date: 2018/7/5 0005 10:37
 * @Description:
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    UserInfoDao userInfoDao;


    @Override
    public UserInfo findUserInfoByUserName(String username) {

        return userInfoDao.findByUsername(username);
    }
}
