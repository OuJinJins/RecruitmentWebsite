package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationInfo extends RegistrationInfo {
    /**
     * 报名信息中的用户信息
     */
    User user;
}
