package com.redis.shiro2.web;

import com.alibaba.fastjson.JSON;
import com.redis.shiro2.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Controller
public class LoginController {

	private static String sessonPrefix="RR_";

	private static String tokenFlag="SSO_TOKEN";

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping(value="/loginUser")
	public String loginUser(String username, String password, Model model, HttpServletResponse response) {
		


		System.out.println("开始登陆，账户名和密码为： "+username+" ："+password);

		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录
			User user = (User) subject.getPrincipal();

			model.addAttribute("use", user);


            Serializable serializableId = subject.getSession().getId();

			Cookie cookie=new Cookie(tokenFlag,sessonPrefix+serializableId);
			cookie.setPath("/");
			response.addCookie(cookie);


			Object obj = redisTemplate.opsForValue().get(sessonPrefix+serializableId);
			System.err.println(obj);

//            System.err.println(serializableId.toString());
//
//
//            Object obj = redisTemplate.opsForValue().get(serializableId);
//
//
//            System.err.println(JSON.toJSONString(obj));


            return "index";
		} catch (Exception e) {
			return "login";// 返回登录页面
		}

	}

	@RequestMapping("/logOut")
	public String logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// session.removeAttribute("user");
		return "login";
	}

}
