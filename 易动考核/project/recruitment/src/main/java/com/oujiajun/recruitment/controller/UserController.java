package com.oujiajun.recruitment.controller;


import com.alibaba.fastjson.JSONObject;
import com.oujiajun.recruitment.entity.dto.Result;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author oujiajun
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/register")
    public String register(User user,HttpSession session){
        // 注册
        ResultInfo resultInfo = userService.register(user);
        if(resultInfo.getSuccess()){
            session.setAttribute("loginUser",(User)resultInfo.getData());
            return "redirect:/index";
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "register";
        }
    }

    @GetMapping({"/login","/login.html"})
    public String login() {
        return "/login";
    }

    @PostMapping("/user/login")
    public String login(User user, Model model, HttpSession session){
        // 登陆
        ResultInfo resultInfo = userService.login(user);
        if(resultInfo.getSuccess()){
            session.setAttribute("loginUser",(User)resultInfo.getData());
            return "redirect:/index";
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "login";
        }
    }

    @RequestMapping("/user/updateUser")
    @ResponseBody
    public String updateUser(User user,HttpSession session){
        if (user.getUsername() == null){
            return null;
        }
        User before = (User)session.getAttribute("loginUser");
        user.setId(before.getId());
        ResultInfo resultInfo = userService.updateUser(user);
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }else {
            ResultInfo queryResult = userService.queryUserById(before.getId());
            if(queryResult.getSuccess()){
                session.setAttribute("loginUser",queryResult.getData());
                return JSONObject.toJSONString(queryResult.getData());
            }else {
                session.setAttribute("errorMsg",queryResult.getMessage());
            }
        }
        return null;
    }

    @GetMapping({ "/profile","/profile.html"})
    public String toProfile() {
        return "/profile";
    }

    @RequestMapping("/user/getLoginUser")
    @ResponseBody
    public String getLoginUser(HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        return JSONObject.toJSONString(user);
    }
}
