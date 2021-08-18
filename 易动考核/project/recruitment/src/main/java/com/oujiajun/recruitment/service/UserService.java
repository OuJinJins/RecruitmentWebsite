package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Interviewer;
import com.oujiajun.recruitment.entity.po.User;

/**
 * @author oujiajun
 */
public interface UserService {


    /**
     * 招聘官注册服务
     * @param interviewer 招聘官注册数据
     * @return 服务结果
     */
    ResultInfo interviewerRegister(Interviewer interviewer);

    /**
     * 注册服务
     * @param user 用户注册数据
     * @return 服务结果
     */
    ResultInfo register(User user);

    /**
     * 登录服务
     * @param user 用户登录数据
     * @return 服务结果
     */
    ResultInfo login(User user);

    /**
     * 招聘官通过审核
     * @param id 用户id
     * @return 结果集
     */
    ResultInfo passInterview(int id);


    /**
     * 查询用户
     * @param id 用户id
     * @return 服务结果
     */
    ResultInfo queryUserById(int id);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    ResultInfo queryUserByUsername(String username);

    /**
     * 通过用户名查询招聘官
     * @param username 用户名
     * @return 结果集
     */
    ResultInfo queryInterviewerByUsername(String username);

    /**
     * 查询所有未通过审核的招聘官
     * @return 服务结果
     */
    ResultInfo queryAllNoPassInterviewer();
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 服务结果
     */
    ResultInfo updateUser(User user);
}
