package com.datasource.service.impl;



import com.datasource.dao.SqlDataLogMapper;
import com.datasource.pojo.SqlDataLog;
import com.datasource.service.SqlDataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SqlDataLogServiceImpl  implements SqlDataLogService {


    @Autowired
    private SqlDataLogMapper sqlDataLogMapper;

    @Override
    @Transactional
    public List<SqlDataLog> findSqlDataLogS() {


//            List<SqlDataLog> allSqlDataLog = sqlDataLogMapper.findAllSqlDataLog();



        return sqlDataLogMapper.findAllSqlDataLog();
    }
}
