package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RecruitmentInfoDao {

    /**
     * 增加招聘信息
     * @param recruitmentInfo 招聘信息
     * @return 修改行数
     */
    int insertRecruitmentInfo(RecruitmentInfo recruitmentInfo);

    /**
     * 删除招聘信息
     * @param recruitmentInfoId 招聘信息id
     * @return 修改行数
     */
    int deleteRecruitmentInfo(int recruitmentInfoId);

    /**
     * 修改招聘信息
     * @param recruitmentInfo 招聘信息
     * @return 修改行数
     */
    int updateRecruitmentInfo(RecruitmentInfo recruitmentInfo);

    /**
     * 更新面试时间段
     * @param interviewPeriodId 报名信息
     * @param version 版本号
     * @param numberGap 报名人数改变量
     * @return 修改行数
     */
    int updateInterviewPeriod(@Param("interviewPeriodId")int interviewPeriodId, @Param("version") int version, @Param("numberGap")int numberGap);

    /**
     * 查询所有的招聘信息
     * @return 招聘信息
     */
    List<RecruitmentInfo> queryAllRecruitmentInfo();

    /**
     * 根据招聘信息id寻找招聘信息
     * @param recruitmentInfoId 招聘信息id
     * @return 招聘信息
     */
    RecruitmentInfo queryRecruitmentInfoById(int recruitmentInfoId);

    /**
     * 通过招聘官id查询招聘信息
     * @param userId 招聘管用户id
     * @return 招聘信息
     */
    List<RecruitmentInfo> queryRecruitmentInfoByUid(int userId);

    /**
     * 根据招聘信息id寻找面试时间段
     * @param recruitmentInfoId 招聘信息id
     * @return 面试时间段集合
     */
    List<InterviewPeriod> queryInterviewPeriodByRecruitmentInfoId(Integer recruitmentInfoId);

    /**
     * 面试时间段id寻找面试时间段
     * @param interviewPeriodId 面试时间段id
     * @return 面试时间段
     */
    InterviewPeriod queryInterviewPeriodByInterviewPeriodId(Integer interviewPeriodId);
}
