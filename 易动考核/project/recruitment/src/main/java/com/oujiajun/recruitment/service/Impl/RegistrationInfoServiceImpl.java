package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.dao.RegistrationInfoDao;
import com.oujiajun.recruitment.dao.RoomDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import com.oujiajun.recruitment.exception.BizException;
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

    @Autowired
    RoomDao roomDao;
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
        // 查询报名信息
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoById(registrationInfoId);
        if(info == null){
            return new ResultInfo(false,"更新报名信息失败");
        }

        // 更新信息
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setRegistrationInfoId(registrationInfoId);
        registrationInfo.setIsRegistrationPass(true);
        int count = registrationInfoDao.updateRegistrationInfo(registrationInfo);
        if (count < 1){
            return new ResultInfo(false,"更新报名信息失败");

        }
        // 如果已经有聊天室则加入聊天
        Room room = roomDao.queryRoomByRecruitmentInfoId(info.getRecruitmentInfoId());
        if(room != null){
            count = roomDao.insertRoomUser(room.getRoomId(),info.getUserId());
            if (count < 1){
                return new ResultInfo(false,"加入该招聘群聊失败");
            }
        }
        return new ResultInfo(true);
    }

    @Override
    public ResultInfo passOutRegistration(Integer registrationInfoId) {
        // 查询报名信息
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoById(registrationInfoId);
        if(info == null){
            throw new BizException("报名信息丢失");
        }
        // 更新信息
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setRegistrationInfoId(registrationInfoId);
        registrationInfo.setIsRegistrationPass(false);
        int count = registrationInfoDao.updateRegistrationInfo(registrationInfo);
        if (count < 1){
            throw new BizException("更新报名信息失败");
        }
        // 如果已经有聊天室则删除聊天
        Room room = roomDao.queryRoomByRecruitmentInfoId(info.getRecruitmentInfoId());
        if(room != null){
            count = roomDao.deleteRoomUser(room.getRoomId(),info.getUserId());
            if (count < 1){
                throw new BizException("将报名者从聊天室中删除失败");
            }
        }
        return new ResultInfo(true);
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
    public ResultInfo queryRegistrationInfoById(int registrationInfoId) {
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoById(registrationInfoId);
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

    @Override
    public ResultInfo queryUserRegistrationInfoByInterviewPeriodId(int interviewPeriodId) {
        List<UserRegistrationInfo> infoList = registrationInfoDao.queryUserRegistrationInfoByInterviewPeriodId(interviewPeriodId);
        if (infoList != null){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询报名信息错误");
        }
    }

    @Override
    public ResultInfo queryUserRegistrationInfoByRegistrationInfoId(Integer registrationInfoId) {
        RegistrationInfo registrationInfo = registrationInfoDao.queryRegistrationInfoById(registrationInfoId);
        if (registrationInfo != null){
            UserRegistrationInfo userRegistrationInfo = registrationInfoDao.queryUserRegistrationInfo(registrationInfo.getUserId(),registrationInfo.getRecruitmentInfoId());
            if(userRegistrationInfo != null){
                return new ResultInfo(true,userRegistrationInfo);
            }
        }
        return new ResultInfo(false,"查询报名信息错误");
    }

    @Override
    public ResultInfo queryPassUserRegistrationInfo(Integer recruitmentInfoId) {
        List<UserRegistrationInfo> infoList = registrationInfoDao.queryPassUserRegistrationInfo(recruitmentInfoId);
        if (infoList == null){
            throw new BizException("查询报名信息失败");
        }
        return new ResultInfo(true,infoList);
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
            throw new BizException("选择面试时间段失败");
        }
        if (period.getCurrentNumber() >= period.getMaxNumber()){
            throw new BizException("此时间段参加面试人数已满");
        }
        // 乐观锁
        int count = recruitmentInfoDao.updateInterviewPeriod(interviewPeriodId,period.getVersion(),1);
        if(count < 0){
            throw new BizException("选择面试时间段失败");
        }
        count = registrationInfoDao.insertInterviewRegistrationInfo(interviewPeriodId,registrationInfoId);
        if (count < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BizException("选择面试时间段失败");
        }
        return new ResultInfo(true);
    }
}
