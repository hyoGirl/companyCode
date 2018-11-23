package com.atomikos.four.service.impl;

import com.atomikos.four.service.BookService;
import com.atomikos.four.service.ThreeService;
import com.atomikos.four.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class ThreeServiceImpl implements ThreeService {


    @Autowired
    BookService bookService;


    @Autowired
    UserService userService;



    @Override
    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public void delete(int id) throws Exception {

        //这个是主数据库
        bookService.delete(id);

        //模拟主数据库抛出异常
//        int a=100/0;

        //取消第二个异常
        userService.delete(id);

        //第二个是从数据库，方法设置报错
//        userService.delete(id);

    }
}
