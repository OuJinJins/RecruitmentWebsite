package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageDaoTest {

    @Autowired
    MessageDao messageDao;

    @Test
    void insertMessage() {
        int count = messageDao.insertMessage(new Message(null,1,"666",6,false));
        System.out.println(count);
        assert count > 0;
    }

    @Test
    void queryMessageOfRoom() {
        List<Message> messages = messageDao.queryMessageOfRoom(1);
        System.out.println(messages);
        assert messages.size() > 0;
    }
}