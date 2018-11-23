package com.login.only.common;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

@Component
@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class VersionCheckFilter implements Filter {

    String params="";
    String body ="";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        String uri = hreq.getRequestURI();


        String contentType = hreq.getContentType();

        System.out.println(contentType);

        if (uri.contains("uploadImageServlet")) {
            //图像上传的请求，不做处理
            chain.doFilter(req, res);
        } else {
            String reqMethod = hreq.getMethod();
            if ("POST".equals(reqMethod)) {
//
//                HttpServletResponse response = (HttpServletResponse) res;
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/json; charset=utf-8");

                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(hreq);
                //multipart/form-data 文件上传
                if(contentType.equals("application/x-www-form-urlencoded")){
                    Map<String, String[]> parameterMap = hreq.getParameterMap();
                    for(Map.Entry<String, String[]> entry: parameterMap.entrySet()){
                        params+=entry.getKey()+"="+entry.getValue()[0]+"&&";
                    }
                    String finalParams = params.substring(0, params.length() - 2);
                    body= finalParams;
                }else{
                    body= HttpHelper.getBodyString(requestWrapper);
                }
                //如果是POST请求则需要获取 param 参数
                String param = URLDecoder.decode(body, "utf-8");
                //String param = hreq.getParameter("param");
                //json串 转换为Map
                System.out.println("****************************");
                System.out.println(param);
                System.out.println("****************************");
                chain.doFilter(requestWrapper, res);
            } else {
                chain.doFilter(req, res);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}