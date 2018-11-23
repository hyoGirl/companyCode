package com.rabbitmq.java.user.service;

import com.rabbitmq.java.enums.ExchangeEnum;
import com.rabbitmq.java.enums.QueueEnum;
import com.rabbitmq.java.message.QueueMessageService;
import com.rabbitmq.java.user.dao.UserRepository;
import com.rabbitmq.java.user.pojo.UserEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Administrator
 * @Date: 2018/7/10 0010 15:57
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {


    @Autowired
    private UserRepository userRepository;
    /**
     * 消息队列业务逻辑实现
     */
    @Autowired
    private QueueMessageService queueMessageService;

    /**
     * 保存用户
     * 并写入消息队列
     * @param userEntity
     * @return
     */
    public Long save(UserEntity userEntity) throws Exception
    {
        /**
         * 保存用户
         */
        userRepository.save(userEntity);
        /**
         * 将消息写入消息队列
         */
        queueMessageService.send(userEntity.getId(), ExchangeEnum.USER_REGISTER, QueueEnum.USER_REGISTER);

        return userEntity.getId();
    }







}
