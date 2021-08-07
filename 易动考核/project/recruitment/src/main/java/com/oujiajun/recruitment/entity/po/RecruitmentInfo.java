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
public class RecruitmentInfo {

    /**
     * 招聘信息id
     */
    Integer recruitmentInfoId;

    /**
     * 面试官id
     */
    Integer userId;

    /**
     * 招聘职业
     */
    String occupation;

    /**
     * 月薪
     */
    Integer monthlyPay;

    /**
     * 职业要求简介
     */
    String introduction;

    /**
     * 面试开始日期
     */
    LocalDate interviewDateBegin;

    /**
     * 面试结束日期
     */
    LocalDate interviewDateEnd;

    /**
     * 面试开始时间
     */
    LocalTime interviewTimeBegin;

    /**
     * 面试结束时间
     */
    LocalTime interviewTimeEnd;

    /**
     * 一个时间段内最大面试人数
     */
    Integer maxNumber;

}
