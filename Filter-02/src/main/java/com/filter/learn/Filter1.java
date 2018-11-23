package com.filter.learn;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter1 implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        String header = request.getHeader("Authorization");

        if(StringUtils.isEmpty(header)){

            throw new NullPointerException();
        }
        System.out.println("正在执行过滤器1");

        System.out.println(header);

        filterChain.doFilter(request, response);


    }

    @Override
    public void destroy() {

    }
}
