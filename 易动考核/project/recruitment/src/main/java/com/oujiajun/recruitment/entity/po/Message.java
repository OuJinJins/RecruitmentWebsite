package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 信息id
     */
    private Integer messageId;
    /**
     * 接受信息的聊天室id
     */
    private Integer toRoomId;
    /**
     * 信息
     */
    private String message;
    /**
     * 发送消息的用户id
     */
    private Integer fromUserId;
}
