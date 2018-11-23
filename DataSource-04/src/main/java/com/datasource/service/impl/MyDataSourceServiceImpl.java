package com.datasource.service.impl;


import com.datasource.dao.MyDataSourceMapper;
import com.datasource.pojo.MyDatasource;
import com.datasource.service.MyDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyDataSourceServiceImpl implements MyDataSourceService {


    @Autowired
    MyDataSourceMapper myDataSourceMapper;

    @Override
    public List<MyDatasource> findAllDataSources() {


        List<MyDatasource> allDataSources = myDataSourceMapper.findAllDataSources();



        return allDataSources;


    }
}
