package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.RecruitmentApplication;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class RecruitmentInfoDaoTest{

    @Resource
    RecruitmentInfoDao recruitmentInfoDao;

    @Test
    void insertRecruitmentInfo() {
        RecruitmentInfo info = new RecruitmentInfo(1,6,"copier",20,"haha", LocalDate.now(),LocalDate.now(),LocalTime.now(), LocalTime.now(),5);
        int count = recruitmentInfoDao.insertRecruitmentInfo(info);
        assert count>=1;
    }

    @Test
    void deleteRecruitmentInfo() {
        int count = recruitmentInfoDao.deleteRecruitmentInfo(1);
        assert count>=1;
    }

    @Test
    void updateRecruitmentInfo() {
        RecruitmentInfo info = new RecruitmentInfo(1,
                6,
                "copier",
                66,
                "haha",
                LocalDate.now(),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                6);
        int count = recruitmentInfoDao.updateRecruitmentInfo(info);
        assert count>=1;

    }

    @Test
    void queryAllRecruitmentInfo() {
        List<RecruitmentInfo> list = recruitmentInfoDao.queryAllRecruitmentInfo();
        assert list.size()>=1;
    }

    @Test
    void queryRecruitmentInfoByUid() {
        List<RecruitmentInfo> list = recruitmentInfoDao.queryRecruitmentInfoByUid(1);
        assert list.size()>=1;
    }
}