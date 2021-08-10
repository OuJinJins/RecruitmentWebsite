package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;

/**
 * @author oujiajun
 */
public interface RecruitmentInfoService {

    /**
     * 增加招聘信息
     * @param recruitmentInfo 招聘信息
     * @return 服务结果集
     */
    ResultInfo insertRecruitmentInfo(RecruitmentInfo recruitmentInfo);

    /**
     * 删除招聘信息
     * @param recruitmentInfoId 招聘信息id
     * @return 服务结果集
     */
    ResultInfo deleteRecruitmentInfo(int recruitmentInfoId);

    /**
     * 修改招聘信息
     * @param recruitmentInfo 招聘信息
     * @return 服务结果集
     */
    ResultInfo updateRecruitmentInfo(RecruitmentInfo recruitmentInfo);

    /**
     * 查询所有招聘信息
     * @return 服务结果集
     */
    ResultInfo queryAllRecruitmentInfo();

    /**
     * 根据招聘信息id查询招聘信息
     * @param recruitmentInfoId 招聘信息id
     * @return 服务结果集
     */
    ResultInfo queryRecruitmentInfoById(int recruitmentInfoId);

    /**
     * 寻找用户发布的招聘信息
     * @param userId 用户id
     * @return 服务结果集
     */
    ResultInfo queryRecruitmentInfoByUid(int userId);

    /**
     * 根据招聘信息id寻找面试时间段
     * @param recruitmentInfoId 招聘信息id
     * @return 服务结果集
     */
    ResultInfo queryInterviewPeriodByRecruitmentInfoId(int recruitmentInfoId);
}
