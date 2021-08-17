package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author oujiajun
 */
public interface RoleService {
    /**
     * 给用户赋予身份
     * @param roleId 身份id
     * @param userId 用户id
     * @return 改变行数
     */
    ResultInfo insertRoleUser(@Param("roleId") int roleId, @Param("userId") int userId);

    /**
     * 通过用户id寻找身份
     * @param userId 用户id
     * @return 身份集合
     */
    ResultInfo queryRoleByUserId(@Param("userId") int userId);
}
