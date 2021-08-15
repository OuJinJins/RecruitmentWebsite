package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.po.Room;
import com.oujiajun.recruitment.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVo extends Room {
    private List<User> userList;
}
