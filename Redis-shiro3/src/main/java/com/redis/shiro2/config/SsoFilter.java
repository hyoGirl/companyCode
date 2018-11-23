package com.redis.shiro2.config;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "SsoFilter",urlPatterns = "/*")
public class SsoFilter implements Filter {


    Logger logger= LoggerFactory.getLogger(SsoFilter.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static String sessonPrefix = "RR_";

    private static String tokenFlag = "SSO_TOKEN";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpServletRequest.getRequestURI();




        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies!=null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenFlag)) {
                    String value = cookie.getValue();
                    Session sso = (Session) redisTemplate.opsForValue().get(value);
                    if (sso != null) {
                        logger.info("************开始从cookie中获取值****************");
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }else{
                        httpServletResponse.sendRedirect("http://localhost:8073/login");
                    }
                }
            }
        }else{
            httpServletResponse.sendRedirect("http://localhost:8073/login");
        }
    }

    @Override
    public void destroy() {


    }
}
