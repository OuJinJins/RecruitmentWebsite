package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.PermissionDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Permission;
import com.oujiajun.recruitment.entity.po.Role;
import com.oujiajun.recruitment.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author oujiajun
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public ResultInfo queryPermissionByUserId(int userId) {
        List<Permission> infoList = permissionDao.queryPermissionByUserId(userId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询权限信息错误");
        }
    }
}
