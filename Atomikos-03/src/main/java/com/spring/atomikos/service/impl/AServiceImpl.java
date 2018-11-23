package com.spring.atomikos.service.impl;

import com.spring.atomikos.entity.A;
import com.spring.atomikos.mapper.master.AMapepr;
import com.spring.atomikos.service.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AServiceImpl implements AService {

    @Autowired
    private AMapepr aMapper;

    @Override
    public boolean SaveA(A a) {

        return aMapper.insert(a) > 0;
    }

    @Override
    public void delete(Integer id) {

        A a=new A();

        a.setId(id);

        aMapper.delete(a);

    }

}