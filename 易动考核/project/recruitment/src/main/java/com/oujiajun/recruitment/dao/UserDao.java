package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author oujiajun
 */
@Mapper
public interface UserDao {

    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 操作行数
     */
    int insertUser(User user);

    /**
     * 查询用户数据
     * @param user 用户数据 UsernameAndPassword
     * @return 用户数据
     */
    User queryUserByUsernameAndPassword(User user);

    /**
     * 查询用户数据
     * @param id 用户id
     * @return 用户数据
     */
    User queryUserById(int id);

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改行数
     */
    int updateUserById(User user);
}
