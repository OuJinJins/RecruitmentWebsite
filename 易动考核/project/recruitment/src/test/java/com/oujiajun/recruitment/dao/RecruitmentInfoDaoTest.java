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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class RecruitmentInfoDaoTest{

    @Resource
    RecruitmentInfoDao recruitmentInfoDao;

    @Test
    void insertRecruitmentInfo() {
        RecruitmentInfo info = new RecruitmentInfo(null,6,"copier",20,"haha", LocalDate.now(),LocalDate.now(),LocalTime.now(), LocalTime.now(),5);
        recruitmentInfoDao.insertRecruitmentInfo(info);
    }

    @Test
    void deleteRecruitmentInfo() {
    }

    @Test
    void updateRecruitmentInfo() {
    }

    @Test
    void queryAllRecruitmentInfo() {
    }

    @Test
    void queryRecruitmentInfoByUid() {
    }
}