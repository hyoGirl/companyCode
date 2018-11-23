package com.properties.datasource.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value="classpath:citydatasource.properties")
@ConfigurationProperties(prefix = "city")
public class CityDataSourceConfig {

    private Map<String, DataSource> map = new HashMap<>();

    public Map<String, DataSource> getMap() {
        return map;
    }

    public void setMap(Map<String, DataSource> map) {
        this.map = map;
    }
}
