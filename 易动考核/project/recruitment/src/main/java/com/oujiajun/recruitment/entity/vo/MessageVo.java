package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.po.Message;
import com.oujiajun.recruitment.entity.po.User;
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
public class MessageVo extends Message {
    User fromUser;
}
