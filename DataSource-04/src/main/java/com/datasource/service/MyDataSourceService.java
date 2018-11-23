package com.datasource.service;


import com.datasource.pojo.MyDatasource;

import java.util.List;

public interface MyDataSourceService {


    List<MyDatasource> findAllDataSources();
}
