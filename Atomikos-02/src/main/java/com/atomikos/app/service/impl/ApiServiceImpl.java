package com.atomikos.app.service.impl;

import com.atomikos.app.service.ApiService;
import com.atomikos.app.service.MasterService;
import com.atomikos.app.service.SlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class ApiServiceImpl implements ApiService {


    @Autowired
    MasterService masterService;


    @Autowired
    SlaveService slaveService;


    @Override
    @Transactional(rollbackFor = { Exception.class, SQLException.class })
    public void delete(long id)  {

        //这个是主数据库
        masterService.delete((int) id);

        // 这个是从数据库，设置了回滚
        slaveService.delete((int) id);



    }
}
