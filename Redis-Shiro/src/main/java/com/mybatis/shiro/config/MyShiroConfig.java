package com.mybatis.shiro.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mybatis.shiro.config.cache.RedisCacheManager;
import com.mybatis.shiro.config.cache.RedisSessionDAO;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class MyShiroConfig {
	
	
	
	@Autowired
	RedisSessionDAO redisSessionDAO;
	
	@Autowired
	RedisCacheManager redisCacheManager;

	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

		bean.setSecurityManager(manager);
		// 配置登录的url和登录成功的url
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/home");
		// 登录成功后要跳转的链接
		bean.setSuccessUrl("/index");
		//未授权界面;
		bean.setUnauthorizedUrl("/403");
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//		filterChainDefinitionMap.put("/css/*", "anon"); // 表示可以匿名访问
//		filterChainDefinitionMap.put("/loginUser", "anon");
//		filterChainDefinitionMap.put("/logout*", "anon");
//		filterChainDefinitionMap.put("/jsp/error.jsp*", "anon");
//		filterChainDefinitionMap.put("/jsp/index.jsp*", "authc");
//		filterChainDefinitionMap.put("/*", "authc");// 表示需要认证才可以访问
//		filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问
//		filterChainDefinitionMap.put("/*.*", "authc");
		
		filterChainDefinitionMap.put("/static/**", "anon");
		//访问前端CSS等不需要拦截
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		//登录页面进行跳转的时候如果成功不需要验证
		filterChainDefinitionMap.put("/loginUser", "anon");
		filterChainDefinitionMap.put("/user/logout", "anon");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		
		filterChainDefinitionMap.put("/**", "authc");
		
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	
	
	
	@Bean
    public DefaultWebSessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setGlobalSessionTimeout(1800);


        sessionManager.setCacheManager(redisCacheManager);



//	        Cookie sessionIdCookie = sessionManager.getSessionIdCookie();
//	        sessionIdCookie.setPath("/");
//	        sessionIdCookie.setName("csid");
//	        sessionManager.setSessionIdCookieEnabled(true);
//	        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
	
	
	
	
	
	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") MybatisRealm authRealm) {
		
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		
		// 自定义session管理 使用redis
		manager.setSessionManager(sessionManager());

		//自定义缓存实现
//		manager.setCacheManager(redisCacheManager);


		manager.setRealm(authRealm);
		
		System.err.println("--------------shiro已经加载安全管理器----------------");
		
		return manager;
	}
	
	

	
	

	

	// 配置自定义的权限登录器
	@Bean(name = "authRealm")
	public MybatisRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		MybatisRealm authRealm = new MybatisRealm();
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}

	// 配置自定义的密码比较器
	@Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
	
	
	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
		
		
	         return daap;
	}
	
	// 管理shiro生命周期
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
	         return new LifecycleBeanPostProcessor();
	}
	

	//解決标签问题
	@Bean  
	public ShiroDialect shiroDialect() {  
	    return new ShiroDialect();  
	}  


	

}
