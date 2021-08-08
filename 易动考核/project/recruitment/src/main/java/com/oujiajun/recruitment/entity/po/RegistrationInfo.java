package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationInfo {

    /**
     * 报名信息id
     */
    Integer registrationInfoId;

    /**
     * 招聘信息id
     */
    Integer recruitmentInfoId;

    /**
     * 报名用户id
     */
    Integer userId;

    /**
     * 是否通过报名
     */
    Boolean isRegistrationPass;

    /**
     * 是否通过面试
     */
    Boolean isInterviewPass;

    /**
     * 面试日期
     */
    LocalDate interviewDate;

    /**
     * 面试时间段开始时间
     */
    LocalTime interviewTimeBegin;

    /**
     * 面试时间段结束时间
     */
    LocalTime interviewTimeEnd;

}
