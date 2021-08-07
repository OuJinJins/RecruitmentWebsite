package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            request.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/interviewer/myRecruitment";
    }


}
