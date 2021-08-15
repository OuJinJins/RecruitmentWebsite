package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.RoomVo;
import com.oujiajun.recruitment.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author oujiajun
 */
@Controller
public class RoomController {

    @Autowired
    RoomService roomService;

    @RequestMapping("/room/getRoom")
    @ResponseBody
    public Object getRoom(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser==null){
            //TODO
            return null;
        }
        ResultInfo info = roomService.queryRoomVoByUserId(loginUser.getId());
        if (!info.getSuccess()) {
            //TODO
            return null;
        }
        return (List<RoomVo>)info.getData();
    }

}
