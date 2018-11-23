package com.atomikos.four.service;


import com.atomikos.four.entity.Book;

public interface BookService {


    void delete(int id);

    void insert(Book book);
}
