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
     * 工作城市
     */
    String workCity;

    /**
     * 公司名称
     */
    String company;

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

    public RecruitmentInfo(String occupation, Integer monthlyPay, String workCity, String company, String introduction, LocalDate interviewDateBegin, LocalDate interviewDateEnd) {
        this.occupation = occupation;
        this.monthlyPay = monthlyPay;
        this.workCity = workCity;
        this.company = company;
        this.introduction = introduction;
        this.interviewDateBegin = interviewDateBegin;
        this.interviewDateEnd = interviewDateEnd;
    }
}
