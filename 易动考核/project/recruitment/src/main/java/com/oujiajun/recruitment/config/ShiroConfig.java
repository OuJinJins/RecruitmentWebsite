package com.oujiajun.recruitment.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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

    /**
     * 用来声明bean 相当于在spring配置文件中配置<bean>标签
     * 配置加密方式
     * @return HashedCredentialsMatcher
     */
    @Bean
    HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置属性值
        //设置加密算法
        matcher.setHashAlgorithmName("MD5");
        //设置加密次数
        matcher.setHashIterations(1024);
        return matcher;
    }

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
        map.put("/interviewer/*","role:interviewer");
        // 静态资源
        map.put("/images/**","anon");
        map.put("/js/**","anon");
        map.put("/css/**","anon");
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

    /**
     * 配置shiro方言，整合thymeleaf时使用，使其支持shiro标签
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 创建一个realm对象
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        //设置加密方式
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }
}

