package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RoleDao;
import com.oujiajun.recruitment.dao.UserDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Role;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author oujiajun
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Override
    public ResultInfo register(User user) {
        // MD5加密
        SimpleHash md5 = new SimpleHash(
                "MD5",
                user.getPassword(),
                user.getUsername()+"",
                1024
        );
        user.setPassword(String.valueOf(md5));
        if(userDao.insertUser(user) == 0){
            user = userDao.queryUserByUsername(user.getUsername());
            int userId = user.getId();
            roleDao.insertRoleUser(3,userId);
            return new ResultInfo(false);
        }
        return new ResultInfo(true);
    }

    @Override
    public ResultInfo login(User user) {
        // MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        // 查询
        User loginUser = userDao.queryUserByUsernameAndPassword(user);
        if(loginUser == null){
            return new ResultInfo(false,"用户名或密码不正确");
        }
        return new ResultInfo(true,loginUser);
    }

    @Override
    public ResultInfo shiroLogin() {
        return null;
    }

    @Override
    public ResultInfo queryUserById(int id) {
        // 查询
        User user = userDao.queryUserById(id);
        if(user == null){
            return new ResultInfo(false,"该用户不存在");
        }
        return new ResultInfo(true,user);
    }
    @Override
    public ResultInfo queryUserByUsername(String username) {
        // 查询
        User user = userDao.queryUserByUsername(username);
        if(user == null){
            return new ResultInfo(false,"该用户不存在");
        }
        return new ResultInfo(true,user);
    }

    @Override
    public ResultInfo updateUser(User user){
        int result = userDao.updateUserById(user);
        if(result >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"修改失败");
        }
    }
}
