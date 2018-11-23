package com.atomikos.app.dao.book;

import com.atomikos.app.pojo.Book;

public interface MasterMapper {

    boolean deleteBookById(int id);


    boolean addBook(Book book);

}
