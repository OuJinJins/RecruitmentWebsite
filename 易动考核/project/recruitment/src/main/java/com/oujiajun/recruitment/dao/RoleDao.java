package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
public interface RoleDao {
    /**
     * 通过用户id寻找身份
     * @param userId 用户id
     * @return 身份集合
     */
    List<Role> queryRoleByUserId(@Param("userId") int userId);
}
