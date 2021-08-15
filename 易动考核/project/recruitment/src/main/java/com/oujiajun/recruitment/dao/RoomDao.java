package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface RoomDao {
    /**
     * 查询聊天室内的用户
     * @param roomId 聊天室id
     * @return 用户集合
     */
    List<User> queryRoomUser(int roomId);
}
