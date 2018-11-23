package com.atomikos.four.service.impl;


import com.atomikos.four.entity.Book;
import com.atomikos.four.mapper.master.BookMapper;
import com.atomikos.four.mapper.slave.UserMapper;
import com.atomikos.four.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserMapper userMapper;

    @Override

    public void delete(int id) {


        bookMapper.deleteBookById(id);
    }

    @Override
    public void insert(Book book) {

        bookMapper.addBook(book);
    }



}
