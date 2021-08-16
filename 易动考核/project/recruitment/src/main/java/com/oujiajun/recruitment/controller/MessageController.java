package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author oujiajun
 */
@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/chat/getHistoryMessage")
    @ResponseBody
    public Object getHistoryMessage(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null){
            // TODO 更好的处理
            return null;
        }
        ResultInfo info = messageService.readUserMessageVo(loginUser.getId());
        if (!info.getSuccess()){
            return null;
        }
        return info.getData();
    }
}
