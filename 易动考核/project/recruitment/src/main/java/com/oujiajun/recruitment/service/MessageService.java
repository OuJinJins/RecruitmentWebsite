package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Message;

/**
 * @author oujiajun
 */
public interface MessageService {
    /**
     * 保存发送的信息
     * @param message 信息
     * @return 服务结果
     */
    ResultInfo insertMessage(Message message);

    /**
     * 读取用户接受到的信息
     * @param userId 用户id
     * @return 服务结果
     */
    ResultInfo readUserMessageVo(int userId);

    /**
     * 查询聊天室内的用户
     * @param roomId 聊天室id
     * @return 结果集
     */
    ResultInfo queryRoomUser(int roomId);
}
