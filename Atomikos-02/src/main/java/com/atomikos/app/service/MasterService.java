package com.atomikos.app.service;

import com.atomikos.app.pojo.Book;

public interface MasterService {


    void delete(int id);

    void insert(Book book);
}
