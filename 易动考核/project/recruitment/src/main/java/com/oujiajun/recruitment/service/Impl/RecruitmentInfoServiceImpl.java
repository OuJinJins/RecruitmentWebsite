package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
        if (!CollectionUtils.isEmpty(infoList)){
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
        if (!CollectionUtils.isEmpty(infoList)){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }
}
