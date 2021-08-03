package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.UserDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
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

    @Override
    public ResultInfo register(User user) {
        // MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if(userDao.insertUser(user) == 0){
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
}
