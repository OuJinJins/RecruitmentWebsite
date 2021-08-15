package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomDaoTest {

    @Autowired
    RoomDao roomDao;

    @Test
    void queryRoomUser() {
        List<User> userList = roomDao.queryRoomUser(1);
        System.out.println(userList);
        assert userList.size() > 0;
    }

    @Test
    void queryRoomByUserId(){
        List<Room> roomList = roomDao.queryRoomByUserId(6);
        System.out.println(roomList);
        assert roomList.size() > 0;
    }
}