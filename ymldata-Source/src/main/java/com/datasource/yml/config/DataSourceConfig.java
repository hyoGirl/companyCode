package com.datasource.yml.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "rongzi")
public class DataSourceConfig {

    private Map<String, DataSource> ds = new HashMap<>();

    public Map<String, DataSource> getDs() {
        return ds;
    }

    public void setDs(Map<String, DataSource> ds) {
        this.ds = ds;
    }
}
