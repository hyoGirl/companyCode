package com.datasource.dao;


import com.datasource.pojo.SqlDataLog;

import java.util.List;

public interface SqlDataLogMapper {


    List<SqlDataLog> findAllSqlDataLog();
}
