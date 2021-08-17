package com.oujiajun.recruitment.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author oujiajun
 */
@Configuration
public class ShiroConfig {

    // 1. subject -> ShiroFilterFactoryBean
    // @Qualifier("securityManager") 指定 Bean 的名字为 securityManager
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean subject = new ShiroFilterFactoryBean();
        // 设置安全管理器
        // 需要关联 securityManager,通过参数把 securityManager 对象传递过来
        subject.setSecurityManager(securityManager);
        // 添加 Shiro 的内置过滤器=======================
        /*
            anon : 无需认证，就可以访问
            authc : 必须认证，才能访问
            user : 必须拥有 “记住我”功能才能用
            perms : 拥有对某个资源的权限才能访问
            role : 拥有某个角色权限才能访问
         */
        Map<String, String> map = new LinkedHashMap<>();
        // 设置 /user/addUser 这个请求,只有认证过才能访问
        // map.put("/user/addUser","authc");
        // map.put("/user/deleteUser","authc");
        // 设置 /user/ 下面的所有请求,只有认证过才能访问
        map.put("/user/*","authc");
        subject.setFilterChainDefinitionMap(map);
        // 设置登录的请求
        subject.setLoginUrl("/login");
        //============================================
        return subject;
    }

    /**
     * 2. securityManager -> DefaultWebSecurityManager
     * /@Qualifier("userRealm") 指定 Bean 的名字为 userRealm
     * spring 默认的 BeanName 就是方法名
     * name 属性 指定 BeanName
     * @param userRealm userRealm
     * @return DefaultWebSecurityManager
     */
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurity(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //需要关联自定义的 Realm，通过参数把 Realm 对象传递过来
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建一个realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}

