package com.oujiajun.recruitment.dao;

import com.oujiajun.recruitment.entity.po.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author oujiajun
 */
@Mapper
public interface MessageDao {
    /**
     * 插入一条信息
     * @param message 信息
     * @return 改变行数
     */
    int insertMessage(Message message);

    /**
     * 查询聊天室里的信息
     * @param roomId 聊天室id
     * @return 信息集合
     */
    List<Message> queryMessageOfRoom(int roomId);
}
