package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Permission;
import com.oujiajun.recruitment.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface PermissionDao {
    /**
     * 通过用户id寻找权限信息
     * @param userId 用户id
     * @return 权限集合
     */
    List<Permission> queryPermissionByUserId(@Param("userId") int userId);
}
