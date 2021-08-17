package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void insertUser() {
    }

    @Test
    void queryUserByUsernameAndPassword() {
    }

    @Test
    void queryUserById() {
        User user = userDao.queryUserById(6);
        System.out.println(user);
        assert user!=null;
    }

    @Test
    void updateUserById() {
    }
}