package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.UserDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.FileService;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @author oujiajun
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    UserDao userDao;

    @Override
    public ResultInfo uploadProfile(MultipartFile file, User loginUser) throws IOException {
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
        loginUser.setProfile(fileName);
        // 更新用户
        User updatedUser = new User();
        updatedUser.setId(loginUser.getId());
        updatedUser.setProfile(fileName);
        int count = userDao.updateUserById(updatedUser);
        if (count <= 0){
            return new ResultInfo(false,"上传头像失败");
        }
        return new ResultInfo(true,fileName);
    }
}
