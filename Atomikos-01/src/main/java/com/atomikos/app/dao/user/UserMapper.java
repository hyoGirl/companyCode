package com.atomikos.app.dao.user;

import com.atomikos.app.pojo.User;

public interface UserMapper {


    boolean deleteUserById(long id);


    boolean addUser(User user);

    boolean updateUser(User user);
}
