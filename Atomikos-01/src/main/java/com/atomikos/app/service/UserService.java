package com.atomikos.app.service;

import com.atomikos.app.pojo.User;

public interface UserService {

    void delete(long id);

    void insert(User user);
}
