package com.atomikos.app.dao.book;

import com.atomikos.app.pojo.Book;

public interface BookMapper {

    boolean deleteBookById(int id);


    boolean addBook(Book book);

}
