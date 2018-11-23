package com.login.single.service;

import com.login.single.model.LoginUser;

public interface UserService {


    LoginUser findUser(String userName,String password);


}
