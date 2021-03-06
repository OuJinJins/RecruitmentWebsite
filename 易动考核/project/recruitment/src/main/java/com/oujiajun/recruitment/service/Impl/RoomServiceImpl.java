package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RecruitmentInfoDao;
import com.oujiajun.recruitment.dao.RegistrationInfoDao;
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
    RegistrationInfoDao registrationInfoDao;

    // TODO 事务抛异常
    @Override
    @Transactional
    public ResultInfo createInterviewRoom(int recruitmentInfoId) {
        // 若该招聘消息的群聊已经创建则返回
        Room beforeRoom = roomDao.queryRoomByRecruitmentInfoId(recruitmentInfoId);
        if (beforeRoom != null){
           return new ResultInfo(false,"该群聊已创建");
        }
        RecruitmentInfo recruitmentInfo = recruitmentInfoDao.queryRecruitmentInfoById(recruitmentInfoId);
        if (recruitmentInfo == null){
            return new ResultInfo(false,"查询招聘信息失败");
        }
        Room room = new Room(null,recruitmentInfo.getOccupation()+"面试者们",recruitmentInfo.getUserId(),recruitmentInfoId,false,null);
        int count = roomDao.createRoom(room);
        if (count <= 0){
            return new ResultInfo(false,"创建聊天室失败");
        }
        Room createdRoom = roomDao.queryRoomByRecruitmentInfoId(recruitmentInfoId);
        if (createdRoom == null){
            return new ResultInfo(Boolean.FALSE,"创建聊天室失败");
        }
        // 查询通过报名审核的报名信息
        List<RegistrationInfo> registrationInfoList = registrationInfoDao.queryPassRegistrationInfoByRecruitmentInfoId(recruitmentInfoId);
        if (registrationInfoList == null){
            return new ResultInfo(Boolean.FALSE,"创建聊天室失败");
        }
        // 遍历插入
        registrationInfoList.forEach(e->roomDao.insertRoomUser(createdRoom.getRoomId(),e.getUserId()));
        // 面试官插入
        roomDao.insertRoomUser(createdRoom.getRoomId(),recruitmentInfo.getUserId());
        return new ResultInfo(true);
    }

    @Override
    @Transactional
    public ResultInfo createPrivateRoom(int userId, int creatorUserId) {
        // 若该私人聊天群聊已经创建则返回
        Room beforeRoom = roomDao.queryRoomByTwoUser(creatorUserId,userId);
        if (beforeRoom != null){
            return new ResultInfo(false,"该聊天已创建");
        }
        Room room = new Room(null,null,creatorUserId,null,true,userId);
        int count = roomDao.createRoom(room);
        if (count <= 0){
            return new ResultInfo(false,"创建聊天室失败");
        }
        Room createdRoom = roomDao.queryRoomByTwoUser(creatorUserId,userId);
        if (createdRoom == null){
            return new ResultInfo(false,"创建聊天室失败");
        }
        roomDao.insertRoomUser(createdRoom.getRoomId(),creatorUserId);
        roomDao.insertRoomUser(createdRoom.getRoomId(),userId);
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

    @Override
    public ResultInfo queryRoomByRecruitmentInfoId(int recruitmentInfoId) {
        Room room = roomDao.queryRoomByRecruitmentInfoId(recruitmentInfoId);
        if(room == null){
            return new ResultInfo(false);
        }
        return new ResultInfo(true);
    }
}
