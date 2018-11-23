package com.login.only.common.Filter;

import com.login.only.common.RequestWrapper;
import com.login.only.modules.model.RequestData;
import com.login.only.util.IPutil;
import com.login.only.util.MyRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

//@Component
//@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    private Logger logger= LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        Date beforeTime  =new Date();
        RequestData requestData=new RequestData();

        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;

        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            try {
                requestWrapper = new RequestWrapper(httpServletRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null == requestWrapper ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            requestData.setClient_ip(IPutil.getIpAddr(httpServletRequest));
            String authorization = httpServletRequest.getHeader("Authorization");
            String requestURI = httpServletRequest.getRequestURI();
            String method = httpServletRequest.getMethod();
            requestData.setUrl(requestURI);
            requestData.setHttp_method(method);
            requestData.setHttp_header(authorization);



            requestData.setClient_ip(IPutil.getIpAddr(httpServletRequest));

            Object param=null;
            if("POST".equals(method)){
//                BufferedReader br = requestWrapper.getReader();
//                String str = null,retStr="";
//                while ((str = br.readLine()) != null) {
//                    retStr += str;
//                }
//                br.close();
//                param=retStr;


                param= MyRequestUtil.getBody(requestWrapper);
            }
            requestData.setHttp_body(param);
            Long dateStart = System.currentTimeMillis();

            filterChain.doFilter(requestWrapper, servletResponse);

            Long costTime=System.currentTimeMillis()-dateStart;

            requestData.setCost_time(costTime);

            logger.info(requestData.toString());
            logger.info("hello");
        }
    }


    @Override
    public void destroy() {

    }
}
