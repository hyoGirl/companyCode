package com.atomikos.four.mapper.slave;

import com.atomikos.four.entity.User;

public interface UserMapper {

    boolean deleteUserById(long id);


    boolean addUser(User user);

    boolean updateUser(User user);



}
