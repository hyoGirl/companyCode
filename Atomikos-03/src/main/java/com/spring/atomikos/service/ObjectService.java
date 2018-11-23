package com.spring.atomikos.service;

import com.spring.atomikos.entity.A;

public interface ObjectService {

    boolean Save(A a) throws Exception;


    void  delete(Integer id) throws Exception;
}
