package com.datasource.dao;


import com.datasource.pojo.MyDatasource;

import java.util.List;

public interface MyDataSourceMapper {

     List<MyDatasource> findAllDataSources();
}
