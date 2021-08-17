package com.oujiajun.recruitment.config;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 * 权限认证类，验证权限信息及用户身份信息
 * @author oujiajun
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 让shiro先于service加载
     */
    @Resource
    @Lazy
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录的用户信息
        User admin = (User) principalCollection.getPrimaryPrincipal();
        // 授权信息集合
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色信息
        HashSet<String> roles = new HashSet<>();
        // 拿到登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        // 查询
        // 设置当前用户的权限
        return info;
    }

    /**
     * 身份认证 登陆时判定用户身份信息
     * @param token 身份令牌
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 查询用户是否存在
        ResultInfo resultInfo  = userService.queryUserByUsername(userToken.getUsername());
        if (!resultInfo.getSuccess()){
            //UnknownAccountException
            return null;
        }
        User user = (User)resultInfo.getData();
        // 如果存在，则返回一个AuthenticationInfo对象，
        // shiro会根据返回对象进行身份认证
        /*
          身份认证对象构造
          参数1：指定需要保存到session中的对象
          参数2：数据库中存储的密码
          参数3：盐值  md5加密中使用的盐(一个字符串)，该值需要保存到数据库
          参数4：realm的名称
         */
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        new SimpleByteSource(
                                user.getUsername()+""),
                        getName()
                );

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",user);
        // 密码认证:shiro做
        // 密码可以加密:md5,md5盐值加密
        // 认证中传入user即可以在授权中取出
        return info;
    }

    /**
     * 设置realm名称
     * @param name realm名称
     */
    @Override
    public void setName(String name) {
        super.setName("myRealm");
    }
}
