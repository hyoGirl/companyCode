package com.spring.properties.config;

import com.spring.properties.model.DataSourceModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@PropertySource("classpath:citycode.properties")
@ConfigurationProperties(prefix = "city")
public class CityDataConfig {




    private Map<String, DataSourceModel> map = new HashMap<>();


    public Map<String, DataSourceModel> getMap() {
        return map;
    }

    public void setMap(Map<String, DataSourceModel> map) {
        this.map = map;
    }
}
