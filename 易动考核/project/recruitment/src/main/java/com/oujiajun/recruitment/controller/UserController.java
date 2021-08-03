package com.oujiajun.recruitment.controller;


import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("didi");
        System.out.println(user);
        ResultInfo resultInfo = userService.register(user);
        if(resultInfo.getSuccess()){
            return "/index.html";
        }else {
            return "/register.html";
        }
    }

}
