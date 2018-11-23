package com.spring.transaction.service;


import com.spring.transaction.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class AccountService {

    @Autowired
    AccountMapper accountMapper;


    public void update(double money,int id){

        accountMapper.updateAccount(money,id);

    }


}
