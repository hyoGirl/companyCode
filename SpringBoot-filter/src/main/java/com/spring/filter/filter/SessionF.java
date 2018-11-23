package com.spring.filter.filter;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionF implements Filter {



    private String NO_LOGIN="你还没有登录";


    String[] includeUrls = new String[]{"/login","/register","/user/login.html"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {



    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        request.setAttribute("aa","120");

        System.out.println("filter url:"+uri);
        //是否需要过滤

        if (!containUri(uri)) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 如果session中包含user对象,则是登录状态
            if(session!=null&&session.getAttribute("user") != null){

                filterChain.doFilter(request, response);
            }else{
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    response.getWriter().write(this.NO_LOGIN);
                }else{
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    response.sendRedirect(request.getContextPath()+"/user/login.html");
                }
                return;
            }
        }


    }

    public boolean containUri(String requestURI) {


        for (String includeUrl : includeUrls) {


            if(includeUrl.equals(requestURI)){

                return false;
            }
        }

        return true;
    }


    @Override
    public void destroy() {



    }
}
