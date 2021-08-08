package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import org.springframework.stereotype.Service;

/**
 * @author oujiajun
 */
@Service
public interface RegistrationInfoService {
    /**
     * 增加报名信息
     * @param registrationInfo 报名信息
     * @return 服务结果集
     */
    ResultInfo insertRegistrationInfo(RegistrationInfo registrationInfo);

    /**
     * 删除报名信息
     * @param registrationInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo deleteRegistrationInfo(int registrationInfoId);

    /**
     * 修改报名信息
     * @param registrationInfo 报名信息
     * @return 服务结果集
     */
    ResultInfo updateRegistrationInfo(RegistrationInfo registrationInfo);

    /**
     * 查询所有报名信息
     * @return 服务结果集
     */
    ResultInfo queryAllRegistrationInfo();

    /**
     * 根据报名信息id查询报名信息
     * @param registrationInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo queryRegistrationInfoById(int registrationInfoId);

    /**
     * 寻找用户发布的报名信息
     * @param userId 用户id
     * @return 服务结果集
     */
    ResultInfo queryRegistrationInfoByUid(int userId);
}
