package com.login.single.web;

import com.login.single.model.LoginUser;
import com.login.single.util.SessionManager;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 这个过滤器主要是用来处理登录/退出以外的操作时，判断是否强制下线
 *
 *
 * @Author xulei
 * @Date 18:15 2018/10/19
 * @Param
 * @return
 **/
public class MyFilter implements Filter {

    private Set<String> excludeUrl = new HashSet<>(Arrays.asList("/login", "/loginOut","/css/**","/fonts/**","/js/**","/favicon.ico"));

    private String[] excludesPattern = new String[]{
            "/login",
            "/favicon.ico",
            "/css/**",
            "/fonts/**",
            "/js/**",
            "/loginOut",
            "/login*",
            "/org.apache*",
    };

    private PathMatcher pathMatcher=new AntPathMatcher();

    public  boolean uriIsExist(String uri){
        for(String path: excludesPattern){
            if(pathMatcher.match(path,uri)){
                return  true;
            }
        }
        return false;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession();
        String requestURI = request.getRequestURI();
        if(uriIsExist(requestURI)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;

        }
        LoginUser loginUser = (LoginUser) currentSession.getAttribute("loginUser");
        //如果user为空，可能是session过期，或者是被强制清空了。必须重新登录
        if (loginUser == null) {
            response.sendRedirect(request.getServletContext() + "/login");
        } else {
            Map<String, String> sessionMap = SessionManager.getSessionMap();
            //获取session中保存的用户名称。
            String userName = loginUser.getUserName();

            //获取已经存在的该用户所保存的sessionID.
            String beforeSessionID = sessionMap.get(userName);
            //如果现在登录成功的同名用户的sessionid和已经存在的id不一致，表示同样名字的session会话在后面产生了,又有一个人登录上来了。
            //本次sessionid已经是属于要被强制下线的session了。

            /**
             * 核心代码是在公用map中，如果存在同名用户，就删除上一次会话，保存新的会话。这个map里面一直保存最新的session ID
             */
            if (!currentSession.getId().equals(beforeSessionID)) {

               String requestPath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
               response.sendRedirect(requestPath + "/login");

            } else {
                //表示是最近登录上来的用户的操作。可以放行
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
