package com.spring.atomikos.service;

import com.spring.atomikos.entity.B;

public interface BService {

    boolean SaveB(B b) throws Exception;

    void  delete(Integer id) throws Exception;
}
