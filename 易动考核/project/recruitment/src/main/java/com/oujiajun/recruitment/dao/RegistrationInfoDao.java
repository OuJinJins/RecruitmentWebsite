package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RegistrationInfoDao {


    /**
     * 增加报名信息
     * @param registrationInfo 报名信息
     * @return 修改行数
     */
    int insertRegistrationInfo(RegistrationInfo registrationInfo);

    /**
     * 删除报名信息
     * @param registrationInfoId 报名信息id
     * @return 修改行数
     */
    int deleteRegistrationInfo(Integer registrationInfoId);

    /**
     * 根据用户id和招聘信息id删除报名信息
     * @param userId 用户id
     * @param recruitmentInfoId 招聘信息id
     * @return 修改行数
     */
    int deleteRegistrationInfoByUidAndRid(@Param("userId") Integer userId, @Param("recruitmentInfoId")Integer recruitmentInfoId);

    /**
     * 修改报名信息
     * @param registrationInfo 报名信息
     * @return 修改行数
     */
    int updateRegistrationInfo(RegistrationInfo registrationInfo);

    /**
     * 查询所有的报名信息
     * @return 报名信息
     */
    List<RegistrationInfo> queryAllRegistrationInfo();

    /**
     * 根据报名信息id寻找报名信息
     * @param registrationInfoId 报名信息id
     * @return 报名信息
     */
    RegistrationInfo queryRegistrationInfoById(Integer registrationInfoId);

    /**
     * 通过报名者id查询报名信息
     * @param userId 报名用户id
     * @return 报名信息
     */
    List<RegistrationInfo> queryRegistrationInfoByUid(Integer userId);

    /**
     * 根据用户id与招聘信息id寻找报名信息
     * @param userId 用户id
     * @param recruitmentInfoId 招聘信息id
     * @return 报名信息
     */
    RegistrationInfo queryRegistrationInfoByUidAndRid(@Param("userId") Integer userId, @Param("recruitmentInfoId")Integer recruitmentInfoId);

    /**
     * 寻找用户报名信息
     * @param userId 用户id
     * @param recruitmentInfoId 招聘信息id
     * @return 用户报名信息视图类
     */
    UserRegistrationInfo queryUserRegistrationInfo(@Param("userId") Integer userId, @Param("recruitmentInfoId")Integer recruitmentInfoId);


    /**
     * 寻找用户报名信息
     * @param recruitmentInfoId 招聘信息id
     * @return 用户报名信息视图类
     */
    List<UserRegistrationInfo> queryUserRegistrationInfoForInterviewer(@Param("recruitmentInfoId")Integer recruitmentInfoId);

    /**
     * 寻找用户报名信息
     * @param interviewPeriodId 时间段id
     * @return 用户报名信息视图类
     */
    List<UserRegistrationInfo>  queryUserRegistrationInfoByInterviewPeriodId(@Param("interviewPeriodId")int interviewPeriodId);

    /**
     * 插入面试时间报名信息
     * @param interviewPeriodId 面试时间段id
     * @param registrationInfoId 报名信息id
     * @return 改变行数
     */
    int insertInterviewRegistrationInfo(@Param("interviewPeriodId")int interviewPeriodId,@Param("registrationInfoId")int registrationInfoId);
}
