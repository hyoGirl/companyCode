package com.atomikos.four;


import com.atomikos.four.config.MasterConfig;
import com.atomikos.four.config.SlaveConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = { MasterConfig.class, SlaveConfig.class })
public class Atomikos4App {

    public static void main(String[] args) {

        SpringApplication.run(Atomikos4App.class,args);
    }
}
