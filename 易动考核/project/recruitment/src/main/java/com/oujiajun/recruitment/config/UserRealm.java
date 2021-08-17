package com.oujiajun.recruitment.config;

import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author oujiajun
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //        用户名,密码到数据库中取
        //        链接真实的数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){//没有这个人
            return null;//UnknownAccoutException
        }
        //        密码认证:shiro做
        //         密码可以加密:md5,md5盐值加密
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");//认证中传入user即可以在授权中取出
    }
}
