package com.atomikos.four.mapper.master;

import com.atomikos.four.entity.Book;

public interface BookMapper {

    boolean deleteBookById(int id);


    boolean addBook(Book book);
}
