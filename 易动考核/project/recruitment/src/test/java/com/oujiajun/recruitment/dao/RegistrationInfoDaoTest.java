package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationInfoDaoTest {

    @Autowired
    RegistrationInfoDao registrationInfoDao;

    @Test
    void insertRegistrationInfo() {
        RegistrationInfo info = new RegistrationInfo(
                1,
                11,
                6,
                false,
                false,
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now()
        );
        int count = registrationInfoDao.insertRegistrationInfo(info);
        assert count > 0;
    }

    @Test
    void deleteRegistrationInfo() {
        int count = registrationInfoDao.deleteRegistrationInfo(1);
        assert count > 0;
    }

    @Test
    void updateRegistrationInfo() {
        RegistrationInfo info = new RegistrationInfo(
                1,
                11,
                6,
                true,
                false,
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now()
        );
        int count = registrationInfoDao.updateRegistrationInfo(info);
        assert count > 0;
    }

    @Test
    void queryAllRegistrationInfo() {
        List list = registrationInfoDao.queryAllRegistrationInfo();
        assertNotNull(list);
    }

    @Test
    void queryRegistrationInfoById() {
        RegistrationInfo info = registrationInfoDao.queryRegistrationInfoById(1);
        assertNotNull(info);
    }

    @Test
    void queryRegistrationInfoByUid() {
        List list = registrationInfoDao.queryRegistrationInfoByUid(6);
        assertNotNull(list);
    }

    @Test
    void queryUserRegistrationInfo() {
        UserRegistrationInfo userRegistrationInfo = registrationInfoDao.queryUserRegistrationInfo(6,1);
        System.out.println(userRegistrationInfo);
        System.out.println(userRegistrationInfo.getUser());
        assertNotNull(userRegistrationInfo);
    }
}