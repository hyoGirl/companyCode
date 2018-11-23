package com.filter.learn;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebConfig {

    @Bean
    public FilterRegistrationBean filter1(){

        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new Filter1());

        filterRegistrationBean.setName("filter1");

        filterRegistrationBean.addUrlPatterns("/*");



        return filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filter2(){

        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new Filter2());
        filterRegistrationBean.setName("filter2");

        filterRegistrationBean.addUrlPatterns("/test2","/test3");
        //按照order值的大小，从小到大的顺序来依次过滤
//        filterRegistrationBean.setOrder(200);

        return filterRegistrationBean;
    }

}
