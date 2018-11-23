package com.datasource.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.datasource.pojo.MyDatasource;
import com.datasource.service.MyDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@DependsOn("dataSourceConfig")
public class InitOtherDataSource {

    @Autowired
    MyDataSourceService myDataSourceService;



    @PostConstruct
    public  void init(){

        List<MyDatasource> allDataSources = myDataSourceService.findAllDataSources();

//        System.out.println(allDataSources);


        createDruidDataSource(allDataSources);
    }


    private void createDruidDataSource(List<MyDatasource> allDataSources) {



//        Map<Object, Object> dataSourceMap = instance.getDataSourceMap();

        Map<Object,Object> datasourceMap=new HashMap<Object,Object>();

        DynamicDataSource instance = DynamicDataSource.getInstance();

        for (MyDatasource myDatasource : allDataSources) {
            String databasename = myDatasource.getDatabasename();

            DruidDataSource druid = new DruidDataSource();
            try {
                druid.setUrl(myDatasource.getUrl());
                druid.setUsername(myDatasource.getUsername());
                druid.setPassword(myDatasource.getPassword());
                druid.setDriverClassName(myDatasource.getDriverClassName());

                druid.init();

                System.err.println(databasename+"【数据源开始初始化】");

            } catch (Exception e) {
                e.printStackTrace();
            }
            datasourceMap.put(databasename,druid);
        }
        instance.setTargetDataSources(datasourceMap);

    }


}
