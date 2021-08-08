package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/interviewer/myRecruitment";
    }

    @GetMapping({ "/interviewer/publish","/interviewer/publish.html"})
    public String toPublish(){
        return "/interviewer/publish";
    }


    @RequestMapping("/interview/doPublish")
    public String publishRecruitment(RecruitmentInfo recruitmentInfo,HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        recruitmentInfo.setUserId(loginUser.getId());
        ResultInfo resultInfo = recruitmentInfoService.insertRecruitmentInfo(recruitmentInfo);
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "redirect:/interviewer/myRecruitment";
    }
}
