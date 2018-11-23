package com.login.single.util;

import com.login.single.model.LoginUser;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *   这个代码已经被作废了
 *
 */



@Service
public class LoginManagerService {


    /**
     * 检查是否存在重复的登陆者
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    public boolean getSingleUser(HttpServletRequest request, HttpServletResponse response, LoginUser user) {

        ServletContext servletContext = request.getServletContext();
//        System.err.println(servletContext);

        //用户是否提出成功   Y 代表了成功  N  代表了失败
        String operateFlag = "N";

        //踢除重复登录用户，被踢除用户是否转向登录页面
        boolean forwardFlag = false;

        HttpSession currentSession = request.getSession();

        String sessionId = currentSession.getId();
        currentSession.setAttribute("id", sessionId);
        currentSession.setAttribute("loginUser", user);
        currentSession.setAttribute("createTime", currentSession.getCreationTime());


        //获取在线登录列表
        List<HttpSession> onlineUsers = (List<HttpSession>) servletContext.getAttribute("onlineUsers");

        if (onlineUsers == null || onlineUsers.size() == 0) {
            onlineUsers = new ArrayList();
            onlineUsers.add(currentSession);

            LoginUser userSession = (LoginUser) currentSession.getAttribute("loginUser");

            System.out.println("第一次登陆者是：" + userSession.toString());

            servletContext.setAttribute("onlineUsers", onlineUsers);

        } else {

            //在线列表中存在值了。就是说第一次有人登录进去了，现在是重复登录了。
            for (int i = 0; i < onlineUsers.size(); i++) {

                //
                HttpSession beforeSession = onlineUsers.get(i);


                //登录用户是否重复标志
                boolean duplicationState = false;

                //上一个登录时的账户 以及session 创建的时间
                LoginUser beforeUser = (LoginUser) beforeSession.getAttribute("loginUser");

                //当前登陆的session
                LoginUser currentUser = (LoginUser) currentSession.getAttribute("loginUser");


                //如果存在相同的账户，就删掉上一个账户。同时把新的账户放进去
                if ((currentUser.getUserName().equals(beforeUser.getUserName())) && currentUser != null && beforeUser != null) {

                    duplicationState = true;

                    onlineUsers.add(currentSession);

                    System.out.println("要销毁的登陆者是：" + ((LoginUser) beforeSession.getAttribute("loginUser")).toString());

                    beforeSession.invalidate();

                    operateFlag = "Y";

                    break;
                }

                if (!duplicationState) {
                    onlineUsers.add(currentSession);
                    break;
                }
            }
        }

        //如果用户被设置为要强制下线了
        if (operateFlag.equals("Y")) {
            // 表示需要去进行重定向
            forwardFlag = true;
        }
        return forwardFlag;
    }
}
