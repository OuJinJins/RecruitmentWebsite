package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * @author oujiajun
 */
@Controller
public class RecruitmentInfoController {

    @Autowired
    RecruitmentInfoService recruitmentInfoService;

    @GetMapping({ "/interviewer/myRecruitment","/interviewer/myRecruitment.html"})
    public String toMyRecruitment(HttpServletRequest request, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        ResultInfo resultInfo = recruitmentInfoService.queryRecruitmentInfoByUid(loginUser.getId());
        if(resultInfo.getSuccess()){
            List<RecruitmentInfo> infoList = (List<RecruitmentInfo>)resultInfo.getData();
            request.setAttribute("infoList",infoList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/interviewer/myRecruitment";
    }

    @GetMapping({ "/interviewer/publish","/interviewer/publish.html"})
    public String toPublish(){
        return "/interviewer/publish";
    }

    @GetMapping("/interviewer/myRecruitment/detail/id/{recruitmentInfoId}")
    public String toDetailRecruitmentInfo(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId,HttpServletRequest request,HttpSession session){
        ResultInfo resultInfo = recruitmentInfoService.queryRecruitmentInfoById(recruitmentInfoId);
        if (resultInfo.getSuccess()) {
            System.out.println(resultInfo.getData());
            request.setAttribute("recruitmentInfo",resultInfo.getData());
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/interviewer/recruitmentDetail";
    }

    @RequestMapping("/interview/doPublish")
    public String publishRecruitment(
            @RequestParam(value = "occupation") String occupation,
            @RequestParam(value = "monthlyPay") Integer monthlyPay,
            @RequestParam(value = "workCity") String workCity,
            @RequestParam(value = "company") String company,
            @RequestParam(value = "introduction") String introduction,
            @RequestParam(value = "interviewDateBegin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate interviewDateBegin,
            @RequestParam(value = "interviewDateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate interviewDateEnd,
            HttpSession session){
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo(
                occupation,
                monthlyPay,
                workCity,
                company,
                introduction,
                interviewDateBegin,
                interviewDateEnd);
        User loginUser = (User)session.getAttribute("loginUser");
        recruitmentInfo.setUserId(loginUser.getId());
        ResultInfo resultInfo = recruitmentInfoService.insertRecruitmentInfo(recruitmentInfo);
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "redirect:/interviewer/myRecruitment";
    }
}
