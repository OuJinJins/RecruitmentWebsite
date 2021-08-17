package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RoleDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.Role;
import com.oujiajun.recruitment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public ResultInfo insertRoleUser(int roleId, int userId) {
        int count = roleDao.insertRoleUser(roleId,userId);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false);
        }
    }

    @Override
    public ResultInfo queryRoleByUserId(int userId) {
        List<Role> infoList = roleDao.queryRoleByUserId(userId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询身份信息错误");
        }
    }
}
