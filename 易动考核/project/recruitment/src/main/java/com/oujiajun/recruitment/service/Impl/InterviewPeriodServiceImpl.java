package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.InterviewPeriodDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.service.InterviewPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oujiajun
 */
@Service
public class InterviewPeriodServiceImpl implements InterviewPeriodService {

    @Autowired
    InterviewPeriodDao interviewPeriodDao;

    @Override
    public ResultInfo queryInterviewPeriodByRegistrationInfoId(Integer registrationInfoId) {
        InterviewPeriod interviewPeriod = interviewPeriodDao.queryInterviewPeriodByRegistrationInfoId(registrationInfoId);
        if (interviewPeriod != null){
            return new ResultInfo(true,interviewPeriod);
        }else {
            return new ResultInfo(false,"查询面试时间段错误");
        }
    }
}
