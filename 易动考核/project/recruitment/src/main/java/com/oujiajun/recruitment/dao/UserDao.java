package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Interviewer;
import com.oujiajun.recruitment.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 招聘官注册
     * @param interviewer 招聘官注册
     * @return 改变行数
     */
    int insertInterviewer(Interviewer interviewer);

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
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    User queryUserByUsername(String username);

    /**
     * 通过用户名查询招聘官
     * @param username 用户名
     * @return 招聘官
     */
    Interviewer queryInterviewerByUsername(String username);

    /**
     * 查询所有未通过的招聘官
     * @return 招聘官集合
     */
    List<Interviewer> queryAllNoPassInterviewer();
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改行数
     */
    int updateUserById(User user);

    /**
     * 招聘官通过审核
     * @param userId 用户id
     * @return 改变行数
     */
    int passInterviewer(@Param("id") int userId);
}
