package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.entity.po.Permission;
import com.oujiajun.recruitment.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class PermissionServiceImplTest {

    @Autowired
    PermissionService permissionService;

    @Test
    void queryPermissionByUserId() {
        System.out.println(permissionService.queryPermissionByUserId(6));
    }
}