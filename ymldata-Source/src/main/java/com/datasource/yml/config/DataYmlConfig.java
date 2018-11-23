package com.datasource.yml.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource(value = "classpath:data.yml",factory = MyYmlPropertyFactory.class)
@ConfigurationProperties(prefix = "data")
public class DataYmlConfig {


    private  Map<String,Object> map=new HashMap<String,Object>();

    public Map<String, Object> getYml() {
        return map;
    }

    public void setYml(Map<String, Object> yml) {
        this.map = yml;
    }
}
