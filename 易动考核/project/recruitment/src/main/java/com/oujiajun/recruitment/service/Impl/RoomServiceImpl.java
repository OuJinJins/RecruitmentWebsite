package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.dao.RoomDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.RoomVo;
import com.oujiajun.recruitment.service.RegistrationInfoService;
import com.oujiajun.recruitment.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @author oujiajun
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomDao roomDao;

    @Autowired
    RecruitmentInfoDao recruitmentInfoDao;

    @Autowired
    RegistrationInfoService registrationInfoService;

    // TODO 事务抛异常
    @Override
    @Transactional
    public ResultInfo createInterviewRoom(int recruitmentInfoId) {
        RecruitmentInfo recruitmentInfo = recruitmentInfoDao.queryRecruitmentInfoById(recruitmentInfoId);
        if (recruitmentInfo == null){
            return new ResultInfo(false,"查询招聘信息失败");
        }
        Room room = new Room(null,recruitmentInfo.getOccupation()+"面试者们",false,recruitmentInfo.getUserId(),recruitmentInfoId);
        int count = roomDao.createRoom(room);
        if (count <= 0){
            return new ResultInfo(false,"创建聊天室失败");
        }
        Room createdRoom = roomDao.queryRoomByRecruitmentInfoId(recruitmentInfoId);
        if (createdRoom == null){
            return new ResultInfo(Boolean.FALSE,"创建聊天室失败");
        }

        return new ResultInfo(true);
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

    @Override
    @Transactional
    public ResultInfo queryRoomVoByUserId(int userId) {
        List<Room> rooms = roomDao.queryRoomByUserId(userId);
        if (rooms == null) {
            return new ResultInfo(false, "查询聊天室信息错误");
        }
        List<RoomVo> roomVos = new LinkedList<>();
        for (Room room : rooms) {
            List<User> userList = roomDao.queryRoomUser(room.getRoomId());
            if (userList == null) {
                return new ResultInfo(false, "查询聊天室用户信息错误");
            }
            RoomVo roomVo = new RoomVo();
            roomVo.setRoomId(room.getRoomId());
            roomVo.setRoomName(room.getRoomName());
            roomVo.setCreatorId(room.getCreatorId());
            roomVo.setIsPrivateChat(room.getIsPrivateChat());
            roomVo.setRecruitmentInfoId(room.getRecruitmentInfoId());
            roomVo.setUserList(userList);
            roomVos.add(roomVo);
        }
        return new ResultInfo(true, roomVos);
    }

}
