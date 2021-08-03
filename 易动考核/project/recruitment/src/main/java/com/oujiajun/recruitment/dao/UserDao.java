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
}
