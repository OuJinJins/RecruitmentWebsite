package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.RecruitmentInfo;

/**
 * @author oujiajun
 */
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
     * 查询所有的招聘信息
     * @return 招聘信息
     */
    RecruitmentInfo queryAllRecruitmentInfo();

    /**
     * 通过招聘官id查询招聘信息
     * @param userId 招聘管用户id
     * @return 招聘信息
     */
    RecruitmentInfo queryRecruitmentInfoByUid(int userId);
}
