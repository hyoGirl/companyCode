package com.atomikos.app.service.impl;

import com.atomikos.app.dao.book.BookMapper;
import com.atomikos.app.pojo.Book;
import com.atomikos.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "bookTransactionManager")
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public void delete(int id) {

        bookMapper.deleteBookById(id);
    }

    @Override
    public void insert(Book book) {

        bookMapper.addBook(book);
    }



}
