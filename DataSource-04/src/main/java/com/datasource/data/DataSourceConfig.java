package com.datasource.data;

import com.alibaba.druid.pool.DruidDataSource;
import com.datasource.config.DynamicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.default.url}")
    private String defaultDBUrl;
    @Value("${spring.datasource.default.username}")
    private String defaultDBUser;
    @Value("${spring.datasource.default.password}")
    private String defaultDBPassword;
    @Value("${spring.datasource.default.driver-class-name}")
    private String defaultDBDreiverName;


    @Bean
    @PostConstruct
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = null;
        try {
            dynamicDataSource = DynamicDataSource.getInstance();

            DruidDataSource defaultDataSource = new DruidDataSource();
            defaultDataSource.setUrl(defaultDBUrl);
            defaultDataSource.setUsername(defaultDBUser);
            defaultDataSource.setPassword(defaultDBPassword);
            defaultDataSource.setDriverClassName(defaultDBDreiverName);
            defaultDataSource.init();


            Map<Object,Object> map = new HashMap<>();
            map.put("default", defaultDataSource);
            dynamicDataSource.setTargetDataSources(map);

            System.err.println("default"+"【数据源开始初始化】");

            dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dynamicDataSource;
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(
//            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dynamicDataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:mapper/*.xml"));
//        return bean.getObject();
//
//    }
//
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(
//            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
//            throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}