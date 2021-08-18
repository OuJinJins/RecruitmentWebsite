package com.oujiajun.recruitment.config;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Interviewer;
import com.oujiajun.recruitment.entity.po.Permission;
import com.oujiajun.recruitment.entity.po.Role;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.exception.BizException;
import com.oujiajun.recruitment.exception.NotApprovedException;
import com.oujiajun.recruitment.service.PermissionService;
import com.oujiajun.recruitment.service.RoleService;
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
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    @Lazy
    RoleService roleService;

    @Resource
    @Lazy
    PermissionService permissionService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录的用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 授权信息集合
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 拿到登录的对象
        // 查询
        // 设置当前用户的权限
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        // 查询用户拥有的身份和权限
        ResultInfo roleResult = roleService.queryRoleByUserId(user.getId());
        ResultInfo permissionResult = permissionService.queryPermissionByUserId(user.getId());
        if (roleResult.getSuccess() && permissionResult.getSuccess()) {
            List<Role> roleList = (List<Role>)roleResult.getData();
            List<Permission> permissionList = (List<Permission>)permissionResult.getData();
            List<String> roles = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
            List<String> permissions = permissionList.stream().map(Permission::getPermissionName).collect(Collectors.toList());
            // 授权
            // 授身份
            info.addRoles(roles);
            info.addStringPermissions(permissions);
        }
        return info;
    }

    /**
     * 身份认证 登陆时判定用户身份信息
     * @param token 身份令牌
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException, NotApprovedException{
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 查询用户是否存在
        ResultInfo resultInfo  = userService.queryUserByUsername(userToken.getUsername());
        if (!resultInfo.getSuccess()){
            //UnknownAccountException
            return null;
        }
        User user = (User)resultInfo.getData();
        // 过滤掉没有审核的招聘官
        ResultInfo queryUserResult = userService.queryInterviewerByUsername(user.getUsername());
        if (!resultInfo.getSuccess()){
            throw new BizException("用户信息丢失");
        }
        Interviewer interviewer = (Interviewer) queryUserResult.getData();
        if(interviewer.getIsPass()!= null && (!interviewer.getIsPass())){
            throw new NotApprovedException("招聘官身份尚未通过审核");
        }
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
        // 清空密码
        user.setPassword(null);
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
