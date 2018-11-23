package com.spring.atomikos.service.impl;


import com.spring.atomikos.entity.B;
import com.spring.atomikos.mapper.slave.BMapepr;
import com.spring.atomikos.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
//@Primary
public class BServiceImpl implements BService {

    @Autowired
    private BMapepr bMapper;

    @Override
    public boolean SaveB(B b) throws Exception {

        int count = bMapper.insert(b);

        if(b.getName().length()>5){

            System.err.println("B事务回滚");
            throw new Exception("名称超过5");
        }
        System.err.println("B事务提交");
        return  count >0;

    }

    @Override
    public void delete(Integer id) {

        B b=new B();
        b.setId(id);

        bMapper.delete(b);


//        try {
//            int i=100/0;
//        } catch (Exception e) {
//            throw  new Exception("测试从数据库删除出错");
//        }

        int a=100/0;

    }

}
