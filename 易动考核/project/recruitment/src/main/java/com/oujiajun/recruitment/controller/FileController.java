package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.exception.BizException;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    UserService userService;

    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImages(
            @RequestParam(value = "file") MultipartFile file,
            HttpSession session) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 上传后的路径,即本地磁盘
        String filePath = "D://temp//";
        // UUID新文件名
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        //本地目录和生成的文件名拼接，这一段存入数据库
        String filename = "/temp/" + fileName;
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null){
            throw new BizException("请先登陆");
        }
        loginUser.setProfile(fileName);
        // 更新用户
        User updatedUser = new User();
        updatedUser.setId(loginUser.getId());
        updatedUser.setProfile(fileName);
        ResultInfo resultInfo = userService.updateUser(updatedUser);
        if (!resultInfo.getSuccess()){
            return null;
        }
        return filename;
    }
}
