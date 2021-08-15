package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;

/**
 * @author oujiajun
 */
public interface RoomService {
    /**
     * 查询聊天室内的用户
     * @param roomId 聊天室id
     * @return 结果集
     */
    ResultInfo queryRoomUser(int roomId);
}
