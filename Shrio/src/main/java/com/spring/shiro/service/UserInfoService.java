package com.spring.shiro.service;

import com.spring.shiro.pojo.UserInfo;

/**
 * @Auther: Administrator
 * @Date: 2018/7/5 0005 10:37
 * @Description:
 */
public interface UserInfoService {

    UserInfo findUserInfoByUserName(String username);


}
