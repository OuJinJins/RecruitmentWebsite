package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RoomDao {


    /**
     * 插入room_user中间表
     * @param roomId 聊天室id
     * @param userId 用户id
     * @return 改变行数
     */
    int insertRoomUser(@Param("roomId") int roomId, @Param("userId") int userId);

    /**
     * 创建聊天室
     * @param room 聊天室
     * @return 改变行数
     */
    int createRoom(Room room);

    /**
     * 查询招聘信息对应的聊天室
     * @param recruitmentInfoId 招聘信息id
     * @return 聊天室
     */
    Room queryRoomByRecruitmentInfoId(int recruitmentInfoId);

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

    /**
     * 查询私人聊天
     * @param creatorId 发起者id
     * @param receivedUserId 接受者id
     * @return 聊天室
     */
    Room queryRoomByTwoUser(@Param("creatorId") int creatorId,@Param("receivedUserId") int receivedUserId);
}
