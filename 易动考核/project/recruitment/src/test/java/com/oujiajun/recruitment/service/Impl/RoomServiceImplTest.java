package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomServiceImplTest {

    @Autowired
    RoomService roomService;

    @Test
    void createInterviewRoom() {
        ResultInfo info = roomService.createInterviewRoom(11);
        assert info.getSuccess();
    }

    @Test
    void queryRoomUser() {
    }

    @Test
    void queryRoomVoByUserId() {
    }
}