package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.exception.BizException;
import com.oujiajun.recruitment.service.FileService;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author oujijaun
 */
@Controller
public class FileController {


    @Resource
    FileService fileService;

    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImages(
            @RequestParam(value = "file") MultipartFile file,
            HttpSession session) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null){
            throw new BizException("请先登陆");
        }
        ResultInfo resultInfo = fileService.uploadProfile(file,loginUser);
        if (!resultInfo.getSuccess()){
            return null;
        }
        // 返回filename
        return (String)resultInfo.getData();
    }
}
