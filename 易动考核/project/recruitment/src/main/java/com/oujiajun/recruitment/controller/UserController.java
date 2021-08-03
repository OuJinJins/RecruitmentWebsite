package com.oujiajun.recruitment.controller;


import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author oujiajun
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(User user){
        // 注册
        ResultInfo resultInfo = userService.register(user);
        if(resultInfo.getSuccess()){
            return "redirect:/index.html";
        }else {
            return "redirect:/register.html";
        }
    }

    @RequestMapping("/login")
    public String login(User user, Map<String,Object> map, HttpSession session){
        // 登陆
        ResultInfo resultInfo = userService.login(user);
        if(resultInfo.getSuccess()){
            return "redirect:/index.html";
        }else {
            map.put("msg",resultInfo.getMessage());
            return "/login.html";
        }
    }
}
