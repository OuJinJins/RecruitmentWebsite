package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;

/**
 * @author oujiajun
 */
public interface UserService {
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
     * 查询用户
     * @param id 用户id
     * @return 服务结果
     */
    ResultInfo queryUserById(int id);
}
