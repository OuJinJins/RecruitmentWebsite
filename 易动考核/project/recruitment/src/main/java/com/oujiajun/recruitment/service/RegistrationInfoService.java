package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author oujiajun
 */
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
     * 取消面试报名
     * @param userId 用户id
     * @param registrationInfoId 报名信息id
     * @return 服务结果
     */
    ResultInfo deleteRegistrationInfoByUidAndRid(Integer userId, Integer registrationInfoId);

    /**
     * 修改报名信息
     * @param registrationInfo 报名信息
     * @return 服务结果集
     */
    ResultInfo updateRegistrationInfo(RegistrationInfo registrationInfo);

    /**
     * 报名通过
     * @param registrationInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo passRegistration(@Param("registrationInfoId") Integer registrationInfoId);

    /**
     * 报名不通过
     * @param registrationInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo passOutRegistration(@Param("registrationInfoId") Integer registrationInfoId);


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

    /**
     * 根据用户id与招聘信息id寻找报名信息
     * @param userId 用户id
     * @param recruitmentInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo queryRegistrationInfoByUidAndRid(Integer userId,Integer recruitmentInfoId);

    /**
     * 根据用户id与招聘信息id寻找用户报名信息
     * @param userId 用户id
     * @param recruitmentInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo queryUserRegistrationInfo(Integer userId,Integer recruitmentInfoId);

    /**
     * 根据招聘信息id寻找該照片信息所有用户报名信息
     * @param recruitmentInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo queryUserRegistrationInfoForInterviewer(@Param("recruitmentInfoId") Integer recruitmentInfoId);

    /**
     * 插入面试时间报名信息
     * @param interviewPeriodId 面试时间段id
     * @param registrationInfoId 报名信息id
     * @return 服务结果集
     */
    ResultInfo insertInterviewRegistrationInfo(@Param("interviewPeriodId")int interviewPeriodId,@Param("registrationInfoId")int registrationInfoId);
}
