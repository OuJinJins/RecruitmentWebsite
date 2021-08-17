package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
public interface PermissionService {

    /**
     * 通过用户id寻找权限信息
     * @param userId 用户id
     * @return 结果集
     */
    ResultInfo queryPermissionByUserId(@Param("userId") int userId);
}
