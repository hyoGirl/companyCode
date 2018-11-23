package com.mybatis.shiro.config;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.mybatis.shiro.pojo.Module;
import com.mybatis.shiro.pojo.Role;
import com.mybatis.shiro.pojo.User;
import com.mybatis.shiro.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class MybatisRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	
	//获取权限，查询登录用户具备哪些角色具备哪些权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		System.out.println("开始进行权限检查");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User user = (User)principals.getPrimaryPrincipal();
		
		Set<Role> roles = user.getRoles();
		
		for (Role role : roles) {
			authorizationInfo.addRole(role.getRname());
			
			Set<Module> modules = role.getModules();
			
			for (Module module : modules) {
				
//				System.out.println(module.getMname());
				authorizationInfo.addStringPermission(module.getMname());
			}
		}
		
		
		
		return authorizationInfo;
	}

	// 登录认证过程
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		System.out.println("开始进行登录认证");

		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;


		User user = userService.findUserByUserName(usernamePasswordToken.getUsername());

		if (user != null) {

			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
					super.getName());
			return simpleAuthenticationInfo;

		}
		return null;
	}

}
