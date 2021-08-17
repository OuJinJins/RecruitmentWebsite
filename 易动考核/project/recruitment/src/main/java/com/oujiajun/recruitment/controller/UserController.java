package com.oujiajun.recruitment.controller;


import com.alibaba.fastjson.JSONObject;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import com.oujiajun.recruitment.utils.CheckUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author oujiajun
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public String register(User user,HttpSession session){
        // 注册
        ResultInfo resultInfo = userService.register(user);
        if(resultInfo.getSuccess()){
            session.setAttribute("loginUser",(User)resultInfo.getData());
            return "redirect:/login";
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
        if (!CheckUtils.checkLoginUser(user)){
            session.setAttribute("errorMsg", "请正确填写用户名和密码");
            return "login";
        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //没有认证过
        //封装用户的登录数据,获得令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //登录 及 异常处理
        try {
            //用户登录
            subject.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException uae) {
            //如果用户名不存在
            session.setAttribute("errorMsg", "用户名不存在");
            return "redirect:/login.html";
        } catch (IncorrectCredentialsException ice) {
            //如果密码错误
            session.setAttribute("errorMsg", "密码错误");
            return "redirect:/login.html";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        if (user == null){
            return "redirect:/login";
        }
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
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
