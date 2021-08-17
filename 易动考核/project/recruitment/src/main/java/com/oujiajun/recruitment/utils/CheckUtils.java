package com.oujiajun.recruitment.utils;

import com.oujiajun.recruitment.entity.po.User;

/**
 * @author oujiajun
 */
public class CheckUtils {

    public static Boolean checkLoginUser(User user){
        if(user == null){
            return false;
        }
        if (user.getUsername() == null || user.getPassword() == null){
            return false;
        }
        return true;
    }
}
