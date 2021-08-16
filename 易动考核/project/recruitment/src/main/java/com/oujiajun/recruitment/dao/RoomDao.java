package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RoomDao {


    /**
     * 创建聊天室
     * @param room 聊天室
     * @return
     */
    int createRoom(Room room);

    /**
     * 查询聊天室内的用户
     * @param roomId 聊天室id
     * @return 用户集合
     */
    List<User> queryRoomUser(int roomId);

    /**
     * 通過用戶id拆綫呢用戶加入的聊天室
     * @param userId 用戶id
     * @return 聊天室集合
     */
    List<Room> queryRoomByUserId(int userId);
}
