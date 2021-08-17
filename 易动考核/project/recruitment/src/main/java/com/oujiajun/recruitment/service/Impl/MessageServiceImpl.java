package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.MessageDao;
import com.oujiajun.recruitment.dao.RoomDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Message;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.MessageVo;
import com.oujiajun.recruitment.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author oujiajun VC
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    RoomDao roomDao;

    @Override
    public ResultInfo insertMessage(Message message) {
        int count = messageDao.insertMessage(message);
        if (count > 0){
            return  new ResultInfo(true);
        }else {
            return  new ResultInfo(false,"保存聊天信息失败");
        }
    }

    @Override
    public ResultInfo readUserMessageVo(int userId) {
        List<Room> rooms = roomDao.queryRoomByUserId(userId);
        if (rooms == null){
            return new ResultInfo(false,"查询用户聊天对象失败");
        }
        List<MessageVo> historyMessage = new LinkedList<>();
        for (Room room : rooms) {
            List<MessageVo> messageVoList = messageDao.queryMessageVoOfRoom(room.getRoomId());
            if(messageVoList == null){
                return new ResultInfo(false,"查询与聊天对象的聊天记录失败");
            }
            historyMessage.addAll(messageVoList);
        }
        return new ResultInfo(true,historyMessage);
    }

    @Override
    public ResultInfo queryRoomUser(int roomId) {
        List<User> userList = roomDao.queryRoomUser(roomId);
        if (userList != null){
            return new ResultInfo(true,userList);
        }else {
            return new ResultInfo(false,"查询聊天室用户信息错误");
        }
    }
}
