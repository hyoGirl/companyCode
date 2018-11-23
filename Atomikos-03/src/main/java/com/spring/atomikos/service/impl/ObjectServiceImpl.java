package com.spring.atomikos.service.impl;

import com.spring.atomikos.entity.A;
import com.spring.atomikos.entity.B;
import com.spring.atomikos.service.AService;
import com.spring.atomikos.service.BService;
import com.spring.atomikos.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
//@Primary
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private AService aService;

    @Autowired
    private BService bService;

    @Override
    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public boolean Save(A a) throws Exception {

        if (!aService.SaveA(a)) {
            return false;
        }

//        int i = 1 / 0;

        B b = new B(a);

        try {
            if (!bService.SaveB(b)) {
                return false;
            }
        } catch (Exception e) {
            System.err.println("A事务回滚");
            throw new Exception("我的错，保存B异常");
        }


//        if (!aService.SaveA(a)) {
//            return false;
//        }

        System.err.println("A事务提交");
        return true;

    }

    @Override
    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public void delete(Integer id) throws Exception {


        aService.delete(id);

        bService.delete(id);

//        try {
//            bService.delete(id);
//        } catch (Exception e) {
//            System.out.println("B事务回滚");
//            throw new Exception("我的错，删除B异常");
//        }

        System.err.println("A事务提交");

    }

}