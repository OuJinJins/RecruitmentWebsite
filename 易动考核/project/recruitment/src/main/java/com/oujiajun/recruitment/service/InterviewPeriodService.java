package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import org.apache.ibatis.annotations.Param;

/**
 * @author oujiajun
 */
public interface InterviewPeriodService {

    /**
     * 查询面试时间段
     * @param registrationInfoId 报名信息id
     * @return 服务结果信息集
     */
    ResultInfo queryInterviewPeriodByRegistrationInfoId(Integer registrationInfoId);


}
