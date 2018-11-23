package com.spring.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/7/4 0004 14:53
 * @Description:
 */
@Configuration
public class ShiroConfig {

    //定义自己的realm
    @Bean
    public MyRealm myRealm(){
        MyRealm myShiroRealm = new MyRealm();
        //设置密码加密
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        return myShiroRealm;
    }

    //定义安全管理器
    @Bean
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(this.myRealm());
        return securityManager;
    }




    //定义好过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //配置shiro拦截器链
        Map<String,String> filterMap=new LinkedHashMap<String,String>();
        filterMap.put("/static/**", "anon");
        filterMap.put("/logout", "logout");
        //配置所有的url都要经过认证才可以访问
        filterMap.put("/**","authc");

        //如果不配置，默认去寻找这个登录页面
        shiroFilter.setLoginUrl("/login");
        //登录成功后跳转页面
        shiroFilter.setSuccessUrl("/index");
        //设置没有权限后跳转的页面
        shiroFilter.setUnauthorizedUrl("/403");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }


    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }


}
