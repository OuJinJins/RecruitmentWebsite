package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author oujiajun
 */
@Controller
public class RecruitmentInfoController {

    RecruitmentInfoService recruitmentInfoService;

    @GetMapping({ "/interviewer/myRecruitment","/interviewer/myRecruitment.html"})
    public String toMyRecruitment(HttpServletRequest request) {
        request.setAttribute("recruitment",recruitmentInfoService.queryAllRecruitmentInfo());
        return "/interviewer/myRecruitment";
    }


}
