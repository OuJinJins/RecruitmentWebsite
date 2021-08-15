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
     * 是否为私人聊天
     */
    private Boolean isPrivateChat;
    /**
     * 创建聊天室用户id
     */
    private Integer creatorId;
    /**
     * 聊天室对应招聘信息id
     */
    private Integer recruitmentId;
}
