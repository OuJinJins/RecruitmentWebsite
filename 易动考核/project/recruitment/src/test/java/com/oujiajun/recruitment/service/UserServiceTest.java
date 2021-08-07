package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService = new UserServiceImpl();

    @Test
    public void register(){
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        userService.register(user);
    }

    @Test
    public void getUserService() {
        userService.queryUserById(6);
    }
}