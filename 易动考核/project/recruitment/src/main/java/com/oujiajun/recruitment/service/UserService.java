package com.oujiajun.recruitment.service;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;

/**
 * @author oujiajun
 */
public interface UserService {
    ResultInfo register(User user);
}
