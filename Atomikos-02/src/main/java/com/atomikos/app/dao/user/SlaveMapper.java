package com.atomikos.app.dao.user;

import com.atomikos.app.pojo.Book;

public interface SlaveMapper {

    boolean deleteBookById(int id);


    boolean addBook(Book book);

}
