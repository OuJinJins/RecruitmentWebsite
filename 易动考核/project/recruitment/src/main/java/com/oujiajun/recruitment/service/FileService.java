package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author oujiajun
 */
public interface FileService {
    /**
     * 上传头像
     * @param file 头像文件
     * @param loginUser 登陆用户
     * @return 服务结果
     */
    ResultInfo uploadProfile(MultipartFile file, User loginUser) throws IOException;
}
