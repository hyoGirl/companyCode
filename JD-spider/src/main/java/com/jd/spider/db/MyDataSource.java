package com.jd.spider.db;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MyDataSource {

    public static DataSource getDataSource(){

        DruidDataSource druidDataSource=new DruidDataSource();
        druidDataSource.setPassword("root");
        druidDataSource.setUsername("root");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1/chunjieboot?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&useSSL=true");
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
