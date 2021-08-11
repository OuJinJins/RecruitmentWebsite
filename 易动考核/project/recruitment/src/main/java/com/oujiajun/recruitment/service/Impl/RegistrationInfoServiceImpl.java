package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.dao.RegistrationInfoDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import com.oujiajun.recruitment.service.RegistrationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author oujiajun
 */
@Service
public class RegistrationInfoServiceImpl implements RegistrationInfoService {
    @Autowired
    RegistrationInfoDao registrationInfoDao;

    @Autowired
    RecruitmentInfoDao recruitmentInfoDao;

    @Override
    public ResultInfo insertRegistrationInfo(RegistrationInfo recruitmentInfo) {
        int count = registrationInfoDao.insertRegistrationInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"新增报名信息失败");
        }
    }

    @Override
    public ResultInfo deleteRegistrationInfo(int recruitmentInfoId) {
        int count = registrationInfoDao.deleteRegistrationInfo(recruitmentInfoId);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"删除报名信息失败");
        }
    }

    @Override
    public ResultInfo deleteRegistrationInfoByUidAndRid(Integer userId, Integer registrationInfoId) {
        int count = registrationInfoDao.deleteRegistrationInfoByUidAndRid(userId,registrationInfoId);
        if (count >= 1){
            return new ResultInfo(true,count);
        }else {
            return new ResultInfo(false,"删除报名信息失败");
        }
    }

    @Override
    public ResultInfo updateRegistrationInfo(RegistrationInfo recruitmentInfo) {
        int count = registrationInfoDao.updateRegistrationInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"更新报名信息失败");
        }
    }

    @Override
    public ResultInfo passRegistration(Integer registrationInfoId) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setRegistrationInfoId(registrationInfoId);
        registrationInfo.setIsRegistrationPass(true);
        int count = registrationInfoDao.updateRegistrationInfo(registrationInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"更新报名信息失败");
        }
    }

    @Override
    public ResultInfo passOutRegistration(Integer registrationInfoId) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setRegistrationInfoId(registrationInfoId);
        registrationInfo.setIsRegistrationPass(false);
        int count = registrationInfoDao.updateRegistrationInfo(registrationInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"更新报名信息失败");
        }
    }

    @Override
    public ResultInfo queryAllRegistrationInfo() {
        List<RegistrationInfo> infoList = registrationInfoDao.queryAllRegistrationInfo();
        if (!CollectionUtils.isEmpty(infoList)){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoById(int recruitmentInfoId) {
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoById(recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoByUid(int userId) {
        List<RegistrationInfo> infoList = registrationInfoDao.queryRegistrationInfoByUid(userId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoByUidAndRid(Integer userId, Integer recruitmentInfoId) {
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoByUidAndRid(userId,recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryUserRegistrationInfo(Integer userId, Integer recruitmentInfoId) {
        UserRegistrationInfo info = registrationInfoDao.queryUserRegistrationInfo(userId,recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryUserRegistrationInfoForInterviewer(Integer recruitmentInfoId) {
        List<UserRegistrationInfo> infoList = registrationInfoDao.queryUserRegistrationInfoForInterviewer(recruitmentInfoId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    //TODO 使用异常

    /**
     * 通过报名者参加某一时间段的面试
     * @param interviewPeriodId 面试时间段id
     * @param registrationInfoId 报名信息id
     * @return 服务结果
     */
    @Override
    @Transactional
    public ResultInfo insertInterviewRegistrationInfo(int interviewPeriodId, int registrationInfoId) {
        InterviewPeriod period = recruitmentInfoDao.queryInterviewPeriodByInterviewPeriodId(interviewPeriodId);
        if (period == null){
            return new ResultInfo(false,"选择面试时间段失败");
        }
        if (period.getCurrentNumber() >= period.getMaxNumber()){
            return new ResultInfo(false,"此时间段参加面试人数已满 ");
        }
        // 乐观锁
        int count = recruitmentInfoDao.updateInterviewPeriod(interviewPeriodId,period.getVersion(),1);
        if(count < 0){
            return new ResultInfo(false,"选择面试时间段失败");
        }
        count = registrationInfoDao.insertInterviewRegistrationInfo(interviewPeriodId,registrationInfoId);
        if (count < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultInfo(false,"选择面试时间段失败");
        }
        return new ResultInfo(true);
    }
}
