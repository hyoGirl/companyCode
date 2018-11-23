package com.redis.shiro2.config.shiro;

import com.redis.shiro2.config.redis.RedisSessionDao;
import com.redis.shiro2.pojo.Module;
import com.redis.shiro2.pojo.Role;
import com.redis.shiro2.pojo.User;
import com.redis.shiro2.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

//@Component
public class MyShiroRealm extends AuthorizingRealm {


    private Logger logger= LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserService userService;

    //获取权限，查询登录用户具备哪些角色具备哪些权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("---------开始进行权限检查---------");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();


        User user = (User)principals.getPrimaryPrincipal();


        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            authorizationInfo.addRole(role.getRname());

            Set<Module> modules = role.getModules();

            for (Module module : modules) {

                authorizationInfo.addStringPermission(module.getMname());
            }
        }
        return authorizationInfo;
    }

    // 登录认证过程
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("---------开始进行登录认证---------");

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
