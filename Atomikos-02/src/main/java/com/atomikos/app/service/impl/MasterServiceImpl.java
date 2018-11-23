package com.atomikos.app.service.impl;

import com.atomikos.app.dao.book.MasterMapper;
import com.atomikos.app.dao.user.SlaveMapper;
import com.atomikos.app.pojo.Book;
import com.atomikos.app.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MasterServiceImpl implements MasterService {

    @Autowired
    MasterMapper masterMapper;

    @Autowired
    SlaveMapper slaveMapper;

    @Override
    public void delete(int id) {


        masterMapper.deleteBookById(id);
    }

    @Override
    public void insert(Book book) {

        masterMapper.addBook(book);
    }



}
