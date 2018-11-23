package com.atomikos.four.service;


import com.atomikos.four.entity.User;

public interface UserService {

    void delete(long id);

    void insert(User user);
}
