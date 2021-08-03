package com.oujiajun.recruitment.service.Impl;

import com.oujiajun.recruitment.dao.UserDao;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oujiajun
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public ResultInfo register(User user) {
        if(userDao.insertUser(user) == 0){
            return new ResultInfo(false);
        }
        return new ResultInfo(true);
    }
}
