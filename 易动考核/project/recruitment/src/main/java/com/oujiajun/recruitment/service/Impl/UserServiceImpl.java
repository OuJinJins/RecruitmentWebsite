package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RoleDao;
import com.oujiajun.recruitment.dao.UserDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Interviewer;
import com.oujiajun.recruitment.entity.po.Role;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.exception.BizException;
import com.oujiajun.recruitment.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

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
    @Transactional
    public ResultInfo interviewerRegister(Interviewer interviewer) {
        User query = userDao.queryUserByUsername(interviewer.getUsername());
        if (query != null){
            return new ResultInfo(false,"用户名已存在");
        }
        // MD5加密
        SimpleHash md5 = new SimpleHash(
                "MD5",
                interviewer.getPassword(),
                interviewer.getUsername()+"",
                1024
        );
        interviewer.setPassword(String.valueOf(md5));
        // 插入用户表
        if(userDao.insertUser(interviewer) <= 0){
            throw new BizException("注册招聘官失败");
        }
        // 查询刚刚插入的用户
        Interviewer register = userDao.queryInterviewerByUsername(interviewer.getUsername());
        if(register == null){
            throw new BizException("用户丢失");
        }
        register.setCompany(interviewer.getCompany());
        int userId = register.getId();
        // 插入到招聘官表中
        // 需要经过审核
        register.setIsPass(false);
        if(userDao.insertInterviewer(register) <= 0){
            throw new BizException("注册招聘官失败");
        }
        // 添加身份信息 2 招聘官
        roleDao.insertRoleUser(2,userId);
        return new ResultInfo(true);
    }

    @Override
    @Transactional
    public ResultInfo register(User user) {
        User query = userDao.queryUserByUsername(user.getUsername());
        if (query != null){
            return new ResultInfo(false,"用户名已存在");
        }
        // MD5加密
        SimpleHash md5 = new SimpleHash(
                "MD5",
                user.getPassword(),
                user.getUsername()+"",
                1024
        );
        user.setPassword(String.valueOf(md5));
        // 插入用户表
        if(userDao.insertUser(user) <= 0){
            throw new BizException("注册报名者失败");
        }
        // 查询刚刚插入的用户
        user = userDao.queryUserByUsername(user.getUsername());
        int userId = user.getId();
        // 添加身份信息 3 普通用户
        roleDao.insertRoleUser(3,userId);
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
    public ResultInfo passInterview(int id) {
        int count = userDao.passInterviewer(id);
        if(count <= 0){
            throw new BizException("审核通过失败");
        }
        return new ResultInfo(true);
    }

    @Override
    public ResultInfo queryUserById(int id) {
        // 查询
        User user = userDao.queryUserById(id);
        if(user == null){
            throw new BizException("该用户不存在");
        }
        return new ResultInfo(true,user);
    }
    @Override
    public ResultInfo queryUserByUsername(String username) {
        // 查询
        User user = userDao.queryUserByUsername(username);
        if(user == null){
            throw new BizException("该用户不存在");
        }
        return new ResultInfo(true,user);
    }

    @Override
    public ResultInfo queryInterviewerByUsername(String username) {
        // 查询
        Interviewer interviewer = userDao.queryInterviewerByUsername(username);
        if(interviewer == null){
            throw new BizException("该用户不存在");
        }
        return new ResultInfo(true,interviewer);
    }

    @Override
    public ResultInfo queryAllNoPassInterviewer() {
        // 查询
        List<Interviewer> interviewer = userDao.queryAllNoPassInterviewer();
        if(interviewer == null){
            throw new BizException("查询未通过审核报名者失败");
        }
        return new ResultInfo(true,interviewer);
    }

    @Override
    public ResultInfo updateUser(User user){
        int result = userDao.updateUserById(user);
        if(result >= 1){
            return new ResultInfo(true);
        }else {
            throw new BizException("更新个人信息失败");
        }
    }
}
