package com.datasource.service.impl;


import com.datasource.config.DataSourceContextHolder;
import com.datasource.dao.ProductMapper;
import com.datasource.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Boolean deleteProductByID(Long id) {

        String databasename="DFSS_LOG";

        try {



//            DataSourceContextHolder.setDBType(databasename);


            Boolean aBoolean = productMapper.deleteProduct(id);


        } catch (Exception e) {
            System.err.println("****************测试切换数据库*******************************");

            DataSourceContextHolder.setDBType(databasename);
            productMapper.deleteProduct(id);
        }


        return true;
    }



}

/*        try {

//            String dataSourceType = DataSourceContextHolder.getDataSourceType();
//
//            System.out.println("当前正在使用的数据源是："+dataSourceType);

            //1:怎么去获取当前使用的默认数据源？


            DataSourceContextHolder.setDataSourceType(databasename);

            productMapper.deleteProduct(id);
           // int a=100/0;
        } catch (Exception e) {
            throw  new RuntimeException();

        } finally {
            DataSourceContextHolder.clearDataSourceType();
        }*/