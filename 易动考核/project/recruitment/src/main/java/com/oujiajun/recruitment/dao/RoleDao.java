package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RoleDao {

    /**
     * 给用户赋予身份
     * @param roleId 身份id
     * @param userId 用户id
     * @return 改变行数
     */
    int insertRoleUser(@Param("roleId") int roleId,@Param("userId") int userId);

    /**
     * 通过用户id寻找身份
     * @param userId 用户id
     * @return 身份集合
     */
    List<Role> queryRoleByUserId(@Param("userId") int userId);
}
