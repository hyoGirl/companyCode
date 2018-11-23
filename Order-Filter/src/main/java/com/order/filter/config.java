package com.order.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {


    @Bean
    public FilterRegistrationBean filter1(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new Filter1());
        filterRegistrationBean.setName("filter1");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.addInitParameter("exclusions","/order2");
        return filterRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean filter2(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new Filter2());
        filterRegistrationBean.setName("filter2");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(2);

        return filterRegistrationBean;

    }
}
