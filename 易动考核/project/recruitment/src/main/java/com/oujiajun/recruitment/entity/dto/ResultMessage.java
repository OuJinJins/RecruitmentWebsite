package com.oujiajun.recruitment.entity.dto;

import com.oujiajun.recruitment.entity.po.Message;
import com.oujiajun.recruitment.entity.po.Room;
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
    private Message message;
}
