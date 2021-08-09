package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RegistrationInfoDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import com.oujiajun.recruitment.service.RegistrationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author oujiajun
 */
@Service
public class RegistrationInfoServiceImpl implements RegistrationInfoService {
    @Autowired
    RegistrationInfoDao recruitmentInfoDao;

    @Override
    public ResultInfo insertRegistrationInfo(RegistrationInfo recruitmentInfo) {
        int count = recruitmentInfoDao.insertRegistrationInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"新增报名信息失败");
        }
    }

    @Override
    public ResultInfo deleteRegistrationInfo(int recruitmentInfoId) {
        int count = recruitmentInfoDao.deleteRegistrationInfo(recruitmentInfoId);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"删除报名信息失败");
        }
    }

    @Override
    public ResultInfo deleteRegistrationInfoByUidAndRid(Integer userId, Integer registrationInfoId) {
        int count = recruitmentInfoDao.deleteRegistrationInfoByUidAndRid(userId,registrationInfoId);
        if (count >= 1){
            return new ResultInfo(true,count);
        }else {
            return new ResultInfo(false,"删除报名信息失败");
        }
    }

    @Override
    public ResultInfo updateRegistrationInfo(RegistrationInfo recruitmentInfo) {
        int count = recruitmentInfoDao.updateRegistrationInfo(recruitmentInfo);
        if (count >= 1){
            return new ResultInfo(true);
        }else {
            return new ResultInfo(false,"更新报名信息失败");
        }
    }

    @Override
    public ResultInfo queryAllRegistrationInfo() {
        List<RegistrationInfo> infoList = recruitmentInfoDao.queryAllRegistrationInfo();
        if (!CollectionUtils.isEmpty(infoList)){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoById(int recruitmentInfoId) {
        RegistrationInfo info = recruitmentInfoDao.queryRegistrationInfoById(recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoByUid(int userId) {
        List<RegistrationInfo> infoList = recruitmentInfoDao.queryRegistrationInfoByUid(userId);
        if (!CollectionUtils.isEmpty(infoList)){
            return new ResultInfo(true,infoList);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryRegistrationInfoByUidAndRid(Integer userId, Integer recruitmentInfoId) {
        RegistrationInfo info = recruitmentInfoDao.queryRegistrationInfoByUidAndRid(userId,recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }

    @Override
    public ResultInfo queryUserRegistrationInfo(Integer userId, Integer recruitmentInfoId) {
        UserRegistrationInfo info = recruitmentInfoDao.queryUserRegistrationInfo(userId,recruitmentInfoId);
        if (info != null){
            return new ResultInfo(true,info);
        }else {
            return new ResultInfo(false,"查询招聘信息错误");
        }
    }
}
