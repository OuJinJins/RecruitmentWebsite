package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private Object message;
    /**
     * 发送消息的用户id
     */
    private Integer fromUserId;
    /**
     * 是否为系统消息
     */
    private Boolean isSystem;
}
