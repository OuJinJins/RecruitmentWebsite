package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.entity.dto.PageBean;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.vo.RecruitmentInfoPage;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author oujiajun
 */
@Service
public class RecruitmentInfoServiceImpl implements RecruitmentInfoService {
    @Autowired
    RecruitmentInfoDao recruitmentInfoDao;

    @Override
    public ResultInfo insertRecruitmentInfo(RecruitmentInfo recruitmentInfo) {
        int count = recruitmentInfoDao.insertRecruitmentInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false);
        }
    }

    @Override
    public ResultInfo deleteRecruitmentInfo(int recruitmentInfoId) {
        int count = recruitmentInfoDao.deleteRecruitmentInfo(recruitmentInfoId);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false);
        }
    }

    @Override
    public ResultInfo updateRecruitmentInfo(RecruitmentInfo recruitmentInfo) {
        int count = recruitmentInfoDao.updateRecruitmentInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false);
        }
    }

    @Override
    public ResultInfo queryAllRecruitmentInfo() {
        List<RecruitmentInfo> infoList = recruitmentInfoDao.queryAllRecruitmentInfo();
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryRecruitmentInfoById(int recruitmentInfoId) {
        RecruitmentInfo info = recruitmentInfoDao.queryRecruitmentInfoById(recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryRecruitmentInfoByUid(int userId) {
        List<RecruitmentInfo> infoList = recruitmentInfoDao.queryRecruitmentInfoByUid(userId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryInterviewPeriodByRecruitmentInfoId(int recruitmentInfoId) {
        List<InterviewPeriod> infoList = recruitmentInfoDao.queryInterviewPeriodByRecruitmentInfoId(recruitmentInfoId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询面试时间段错误");
        }
    }

    @Override
    public ResultInfo queryInterviewPeriodByInterviewPeriodId(Integer interviewPeriodId) {
        InterviewPeriod period = recruitmentInfoDao.queryInterviewPeriodByInterviewPeriodId(interviewPeriodId);
        if (period != null){
            return new ResultInfo(true,period);
        }else {
            return new ResultInfo(false,"查询面试时间段错误");
        }
    }

    @Override
    public ResultInfo queryRecruitmentInfoForPage(PageBean pageBean, Map<String, Object> params) {

        int startIndex = pageBean.getStartIndex();
        int pageSize = pageBean.getPageSize();
        params.put("startIndex",startIndex);
        params.put("pageSize",pageSize);
        List<RecruitmentInfo> recruitmentInfoList = recruitmentInfoDao.selectRecruitmentInfoForPage(params);
        if (recruitmentInfoList == null){
            return new ResultInfo(false,"查询招聘信息失败");
        }
        pageBean.setTotalCount(recruitmentInfoList.size());
        RecruitmentInfoPage recruitmentInfoPage = new RecruitmentInfoPage(recruitmentInfoList,pageBean);
        return new ResultInfo(true,recruitmentInfoPage);
    }
}
