package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天室实体类
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    /**
     * 房间id
     */
    private Integer roomId;
    /**
     * 聊天室名称
     */
    private String roomName;
    /**
     * 创建聊天室用户id
     */
    private Integer creatorId;
    /**
     * 聊天室对应招聘信息id
     */
    private Integer recruitmentInfoId;
    /**
     * 是否为私人聊天
     */
    private Boolean isPrivateChat;
    /**
     * 私人聊天另一方用户id
     */
    private Integer receivedUserId;

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", isPrivateChat=" + isPrivateChat +
                ", creatorId=" + creatorId +
                ", recruitmentInfoId=" + recruitmentInfoId +
                '}';
    }
}
