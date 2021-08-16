package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;

/**
 * @author oujiajun
 */
public interface RoomService {
    /**
     * 创建面试成员聊天室
     * @param recruitmentInfoId 招聘信息id
     * @return 服务结果
     */
    ResultInfo createInterviewRoom(int recruitmentInfoId);
    /**
     * 查询聊天室内的用户
     * @param roomId 聊天室id
     * @return 结果集
     */
    ResultInfo queryRoomUser(int roomId);

    /**
     * 通過用戶id拆綫呢用戶加入的聊天室
     * @param userId 用戶id
     * @return 结果集
     */
    ResultInfo queryRoomVoByUserId(int userId);
}
