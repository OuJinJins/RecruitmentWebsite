package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.RoomDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
