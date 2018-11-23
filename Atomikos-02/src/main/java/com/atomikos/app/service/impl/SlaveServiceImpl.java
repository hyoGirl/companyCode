package com.atomikos.app.service.impl;

import com.atomikos.app.dao.book.MasterMapper;
import com.atomikos.app.dao.user.SlaveMapper;
import com.atomikos.app.pojo.Book;
import com.atomikos.app.service.SlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class SlaveServiceImpl implements SlaveService {


    @Autowired
    MasterMapper masterMapper;

    @Autowired
    SlaveMapper slaveMapper;


    @Override
//    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public void delete(int id) {

        slaveMapper.deleteBookById(id);

        int a=100/0;

    }

    @Override
    public void insert(Book book) {


        slaveMapper.addBook(book);

    }
}
