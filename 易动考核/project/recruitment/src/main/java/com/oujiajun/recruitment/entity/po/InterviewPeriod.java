package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewPeriod {
    Integer interviewPeriodId;
    Integer recruitmentInfoId;
    LocalDate interviewDate;
    LocalTime interviewTimeBegin;
    LocalTime interviewTimeEnd;
    Integer maxNumber;
    Integer version;
}
