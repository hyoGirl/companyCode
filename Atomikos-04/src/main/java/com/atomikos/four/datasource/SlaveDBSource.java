package com.atomikos.four.datasource;

import com.atomikos.four.config.SlaveConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.atomikos.four.mapper.slave", sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class SlaveDBSource {

    // 配置从数据源
    @Bean(name = "SlaveDB")
    public DataSource testDataSource(SlaveConfig slaveConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(slaveConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(slaveConfig.getPassword());
        mysqlXaDataSource.setUser(slaveConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        /**
         * 设置分布式 -- 从数据源
         */
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("SlaveDB");

        /**
         * 连接池配置
         */
        xaDataSource.setMinPoolSize(slaveConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(slaveConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(slaveConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(slaveConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(slaveConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(slaveConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(slaveConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(slaveConfig.getTestQuery());

        System.err.println("从数据源注入成功.....");
        return xaDataSource;
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("SlaveDB") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));

        return bean.getObject();
    }

    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate slaveSqlSessionTemplate(
            @Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
