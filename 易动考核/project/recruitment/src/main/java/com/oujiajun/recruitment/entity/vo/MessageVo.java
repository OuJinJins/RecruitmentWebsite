package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.po.Message;
import com.oujiajun.recruitment.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo extends Message {
    User fromUser;
}
