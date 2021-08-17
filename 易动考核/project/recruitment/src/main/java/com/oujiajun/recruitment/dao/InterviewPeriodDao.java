package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author oujiajun
 */
@Mapper
public interface InterviewPeriodDao {

    /**
     * 查询面试时间段
     * @param registrationInfoId 报名信息id
     * @return 面试时间段
     */
     InterviewPeriod queryInterviewPeriodByRegistrationInfoId(@Param("registrationInfoId") Integer registrationInfoId);

}
