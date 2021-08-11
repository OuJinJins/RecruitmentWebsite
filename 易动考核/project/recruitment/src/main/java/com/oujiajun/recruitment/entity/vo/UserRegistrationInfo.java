package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationInfo extends RegistrationInfo {
    /**
     * 报名信息中的用户信息
     */
    User user;

    public UserRegistrationInfo(RegistrationInfo registrationInfo, User user) {
        super(registrationInfo.getRegistrationInfoId(), registrationInfo.getRecruitmentInfoId(), registrationInfo.getUserId(), registrationInfo.getIsRegistrationPass(), registrationInfo.getIsInterviewPass(), registrationInfo.getInterviewDate(), registrationInfo.getInterviewTimeBegin(), registrationInfo.getInterviewTimeEnd());
        this.user = user;
    }
}
