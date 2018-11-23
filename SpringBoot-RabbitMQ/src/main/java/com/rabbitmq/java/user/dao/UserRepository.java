package com.rabbitmq.java.user.dao;

import com.rabbitmq.java.user.pojo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: Administrator
 * @Date: 2018/7/10 0010 15:55
 * @Description:
 */
public interface UserRepository extends JpaRepository<UserEntity,Long> {



}
