package com.atomikos.app.datasource;

import com.atomikos.app.config.BookDBConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.atomikos.app.dao.book", sqlSessionTemplateRef  = "masterSqlSessionTemplate")
public class MasterDataSource {

    // 配置主数据源
    @Primary
    @Bean(name = "MasterDB")
        public DataSource masterDataSource(BookDBConfig masterConfig) throws SQLException {

        /**
         * MySql数据库驱动 实现 XADataSource接口
         */
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(masterConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(masterConfig.getPassword());
        mysqlXaDataSource.setUser(masterConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        /**
         * 设置分布式-- 主数据源
         */
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("MasterDB");

        xaDataSource.setMinPoolSize(masterConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(masterConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(masterConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(masterConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(masterConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(masterConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(masterConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(masterConfig.getTestQuery());

        System.err.println("主数据源注入成功.....");
        return xaDataSource;
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("MasterDB") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        //这个地方必须取消，不然就会出现BUG
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/book/MasterMapper.xml"));



        return bean.getObject();
    }

    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
