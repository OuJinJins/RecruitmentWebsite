package com.oujiajun.recruitment.controller;


import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            return "index";
        }else {
            return "register";
        }
    }

    @GetMapping({"/login"})
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session){
        // 登陆
        ResultInfo resultInfo = userService.login(user);
        if(resultInfo.getSuccess()){
            session.setAttribute("loginUser",(User)resultInfo.getData());
            return "redirect:/user/index";
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "login";
        }
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request,HttpSession session) {
        request.setAttribute("session", session);
        return "/index";
    }


}
