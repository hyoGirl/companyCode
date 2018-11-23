package com.spring.shiro.dao;

import com.spring.shiro.pojo.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Auther: Administrator
 * @Date: 2018/7/5 0005 10:34
 * @Description:
 */
public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

    /**查找用户信息;*/
    public UserInfo findByUsername(String username);


}