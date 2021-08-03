package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void register(){
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        userService.register(user);
    }

}