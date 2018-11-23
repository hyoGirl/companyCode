package com.datasource.service;


import com.datasource.pojo.SqlDataLog;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/8/7 0007
 * @Description:
 */
public interface SqlDataLogService  {


    List<SqlDataLog> findSqlDataLogS();
}
