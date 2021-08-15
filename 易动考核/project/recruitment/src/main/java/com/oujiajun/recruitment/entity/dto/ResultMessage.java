package com.oujiajun.recruitment.entity.dto;

import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.vo.MessageVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private Room room;
    private MessageVo message;
}
