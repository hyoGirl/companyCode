package com.spring.atomikos;

import com.spring.atomikos.config.MasterConfig;
import com.spring.atomikos.config.SlaveConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = { MasterConfig.class, SlaveConfig.class })
public class Atomikos3App {

    public static void main(String[] args) {

        SpringApplication.run(Atomikos3App.class,args);
    }
}
