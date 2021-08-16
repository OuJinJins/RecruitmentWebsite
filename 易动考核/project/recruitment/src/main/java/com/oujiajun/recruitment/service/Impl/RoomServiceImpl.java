package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RoomDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.RoomVo;
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
