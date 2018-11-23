package com.login.log.common.filter;

import com.login.log.common.MyRequestWrapper;
import com.login.log.modules.model.RequestLogData;
import com.login.log.util.IPutil;
import com.login.log.util.MyRequestUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

@Component
@WebFilter(filterName = "RequestLogFilter",urlPatterns = "/*")
public class RequestLogFilter implements Filter {


    Long startTime =null;
    String params="";
    String body =null;

    private Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        RequestLogData requestLogData=new RequestLogData();
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;

        String contentType = httpServletRequest.getContentType();
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();
        String authorization = httpServletRequest.getHeader("Authorization");


        String cityCode = httpServletRequest.getHeader("cityCode");


        String userAgent = httpServletRequest.getHeader("User-Agent");
        requestLogData.setUserAgent(userAgent);
        requestLogData.setUri(requestURI);
        requestLogData.setClientIp(IPutil.getIpAddr(httpServletRequest));
        requestLogData.setAuthorization(authorization);
        requestLogData.setHttpMethod(method);
        requestLogData.setContentType(contentType);

        requestLogData.setCityCode(cityCode);

        ServletRequest requestWrapper =null;
        if("POST".equals(method)){
          requestWrapper = new MyRequestWrapper(httpServletRequest);
            if(contentType.equals("application/x-www-form-urlencoded")){
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                for(Map.Entry<String, String[]> entry: parameterMap.entrySet()){
                    params+=entry.getKey()+"="+entry.getValue()[0]+"&&";
                }
                String finalParams = params.substring(0, params.length() - 2);
                body= finalParams;
            }else if(contentType.equals("multipart/form-data")){
                body=null;
            }else{
                body= MyRequestUtil.getBody(requestWrapper);
            }
        }else{
            requestWrapper=servletRequest;
        }
        startTime =System.currentTimeMillis();
        filterChain.doFilter(requestWrapper,servletResponse);
        requestLogData.setHttpBody(body);
        requestLogData.setCostTime(System.currentTimeMillis()-startTime);
        logger.info(requestLogData.toString());

    }

    @Override
    public void destroy() {

    }
}
