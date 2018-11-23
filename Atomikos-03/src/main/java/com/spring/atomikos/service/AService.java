package com.spring.atomikos.service;

import com.spring.atomikos.entity.A;

public interface AService {

    boolean SaveA(A a);

    void  delete(Integer id);
}
