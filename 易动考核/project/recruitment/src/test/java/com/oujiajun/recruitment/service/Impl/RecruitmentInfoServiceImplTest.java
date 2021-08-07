package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecruitmentInfoServiceImplTest {

    @Autowired
    RecruitmentInfoService recruitmentInfoService;

    @Test
    void insertRecruitmentInfo() {
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
    void queryRecruitmentInfoById() {
        List<RecruitmentInfo> infoList = (List<RecruitmentInfo>)recruitmentInfoService.queryRecruitmentInfoByUid(6).getData();
        System.out.println(infoList);
        assert infoList != null;
    }

    @Test
    void queryRecruitmentInfoByUid() {
    }
}