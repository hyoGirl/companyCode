package com.properties.datasource;

import com.properties.datasource.config.CityDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(value = {CityDataSourceConfig.class})
public class PropertiesApp {

    public static void main(String[] args) {

        SpringApplication.run(PropertiesApp.class,args);
    }
}
