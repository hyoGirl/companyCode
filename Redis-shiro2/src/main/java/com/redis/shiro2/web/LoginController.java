package com.redis.shiro2.web;

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

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Controller
public class LoginController {


    @Autowired
    RedisTemplate redisTemplate;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping(value="/loginUser")
	public String loginUser(String username, String password, Model model) {
		


		System.out.println("开始登陆，账户名和密码为： "+username+" ："+password);

		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录

			User user = (User) subject.getPrincipal();


            Serializable sessionId = subject.getSession().getId();






//            sessionId.toString().getBytes()

//            redisTemplate.opsForValue().set(sessionId.toString().getBytes(),);



            model.addAttribute("use", user);

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
