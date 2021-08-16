package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.RoomVo;
import com.oujiajun.recruitment.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/room/create/id/{recruitmentInfoId}")
    public String createInterviewRoom(
            @PathVariable("recruitmentInfoId") Integer recruitmentInfoId,
            HttpSession session){
        ResultInfo resultInfo = roomService.createInterviewRoom(recruitmentInfoId);
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "redirect:/interviewer/myRecruitment/detail/id/" + recruitmentInfoId;
        }else{
            return "redirect:/chat";
        }
    }

    @GetMapping("/room/create/private/id/{userId}")
    public String createPrivateRoom(
            @PathVariable("userId") Integer userId,
            HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            session.setAttribute("errorMsg","请在登陆后执行此操作");
            return "redirect:/login";
        }
        ResultInfo resultInfo = roomService.createPrivateRoom(userId, loginUser.getId());
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "redirect:/chat";
    }

    @GetMapping("/chat/{recruitmentInfoId}")
    public String toRecruitmentChat(
            @PathVariable("recruitmentInfoId") Integer recruitmentInfoId,
            HttpSession session){
        ResultInfo queryRoomResult = roomService.queryRoomByRecruitmentInfoId(recruitmentInfoId);
        if (queryRoomResult.getSuccess()){
            return "/chat";
        }else {
            session.setAttribute("errorMsg","群聊未建立");
            return "redirect:/recruitment/detail/id/"+recruitmentInfoId;
        }
    }

    @GetMapping({"/chat.html","/chat"})
    public String toChat(){
        return "/chat";
    }

    @RequestMapping({"/room/getRoom","/chat/room/getRoom"})
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
