package com.redis.shiro2.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.redis.shiro2.config.redis.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;

@Configuration
public class Shiro2Config {



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



    /**
     * 安全管理模块，所有的manager在此配置
     * @param redisTemplate
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(RedisTemplate redisTemplate) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //自定义realm
        securityManager.setRealm(myShiroRealm(redisTemplate));

        //自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager(redisTemplate));

    //    //自定义缓存实现 使用redis
        securityManager.setCacheManager(new ShiroRedisCacheManager(redisTemplate));

        //注入记住我管理器;
//        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


    /**
     * 配饰sessionManager
     *
     * @param redisTemplate
     * @return
     */

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        RedisSessionDao redisSessionDao = new RedisSessionDao(redisTemplate);

        sessionManager.setSessionDAO(redisSessionDao);

        return sessionManager;
    }


    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(RedisTemplate redisTemplate) {

        MyShiroRealm shiroRealm = new MyShiroRealm();

        shiroRealm.setCacheManager(redisCacheManager(redisTemplate));
        shiroRealm.setCachingEnabled(true);

        //设置认证密码算法及迭代复杂度
        shiroRealm.setCredentialsMatcher(credentialsMatcher());
        //认证
        shiroRealm.setAuthenticationCachingEnabled(false);
        //授权
        shiroRealm.setAuthorizationCachingEnabled(false);


        return shiroRealm;
    }


    /**
     * 缓存管理器的配置
     * @param redisTemplate
     * @return
     */
    @Bean(name = "ShiroRedisCacheManager")
    public ShiroRedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {

        ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager(redisTemplate);

        redisCacheManager.getCache("shiro_redis_");

        return redisCacheManager;
    }



    /**
     * realm的认证算法
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher() {

        HashedCredentialsMatcher myCredentialsMatcher = new MyCredentialsMatcher();

        myCredentialsMatcher.setHashAlgorithmName("MD5");

//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        myCredentialsMatcher.setHashIterations(2);
        myCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return myCredentialsMatcher;
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
