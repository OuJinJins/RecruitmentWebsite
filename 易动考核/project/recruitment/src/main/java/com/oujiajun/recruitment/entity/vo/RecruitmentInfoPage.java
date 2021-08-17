package com.oujiajun.recruitment.entity.vo;

import com.oujiajun.recruitment.entity.dto.PageBean;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentInfoPage {
    List<RecruitmentInfo> recruitmentInfoList;
    PageBean pageBean;
}
