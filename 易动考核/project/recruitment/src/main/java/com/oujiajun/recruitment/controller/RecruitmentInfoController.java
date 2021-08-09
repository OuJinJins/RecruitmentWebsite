package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import com.oujiajun.recruitment.service.RegistrationInfoService;
import com.oujiajun.recruitment.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author oujiajun
 */
@Controller
public class RecruitmentInfoController {

    @Autowired
    RecruitmentInfoService recruitmentInfoService;

    @Autowired
    UserService userService;

    @Autowired
    RegistrationInfoService registrationInfoService;

    @GetMapping({"", "/", "/index","/index.html"})
    public String index(HttpServletRequest request, HttpSession session) {
        ResultInfo resultInfo = recruitmentInfoService.queryAllRecruitmentInfo();
        if(resultInfo.getSuccess()){
            List<RecruitmentInfo> infoList = (List<RecruitmentInfo>)resultInfo.getData();
            request.setAttribute("infoList",infoList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/index";
    }

    @GetMapping({ "/interviewer/myRecruitment","/interviewer/myRecruitment.html"})
    public String toMyRecruitment(HttpServletRequest request, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        ResultInfo resultInfo = recruitmentInfoService.queryRecruitmentInfoByUid(loginUser.getId());
        if(resultInfo.getSuccess()){
            List<RecruitmentInfo> infoList = (List<RecruitmentInfo>)resultInfo.getData();
            request.setAttribute("myInfoList",infoList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/interviewer/myRecruitment";
    }

    @GetMapping({ "/interviewer/publish","/interviewer/publish.html"})
    public String toPublish(){
        return "/interviewer/publish";
    }

    @GetMapping("/registrationInfo/cancel/id/{recruitmentInfoId}")
    public String deleteRegistrationInfo(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId,HttpServletRequest request,HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        ResultInfo resultInfo = registrationInfoService.deleteRegistrationInfoByUidAndRid(loginUser.getId(), recruitmentInfoId);
        if(!resultInfo.getSuccess()){
            request.setAttribute("errorMsg","请登陆后进行该操作");
        }
        return "redirect:/recruitment/detail/id/" + recruitmentInfoId;
    }
    /**
     * 普通用户前往招聘详情
     * @param recruitmentInfoId 招聘信息id
     * @param request 请求
     * @param session session
     * @return 视图
     */
    @GetMapping("/recruitment/detail/id/{recruitmentInfoId}")
    public String toDetailRecruitmentInfo(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId,HttpServletRequest request,HttpSession session){
        ResultInfo resultInfo = recruitmentInfoService.queryRecruitmentInfoById(recruitmentInfoId);
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        if (resultInfo.getSuccess()) {
            RecruitmentInfo recruitmentInfo = (RecruitmentInfo) resultInfo.getData();
            request.setAttribute("recruitmentInfo",recruitmentInfo);
            ResultInfo userServiceInfo = userService.queryUserById(recruitmentInfo.getUserId());
            if(userServiceInfo.getSuccess()){
                User interviewer = (User)userServiceInfo.getData();
                request.setAttribute("interviewer",interviewer);
                // 检查是否已经报名
                ResultInfo queryRegistrationResult = registrationInfoService.queryRegistrationInfoByUidAndRid(loginUser.getId(),recruitmentInfo.getRecruitmentInfoId());
                if(queryRegistrationResult != null){
                    if(queryRegistrationResult.getSuccess()){
                        request.removeAttribute("registrationInfo");
                        RegistrationInfo registrationInfo = (RegistrationInfo) queryRegistrationResult.getData();
                        request.setAttribute("registrationInfo",registrationInfo);
                    }
                }
            }else {
                session.setAttribute("errorMsg",userServiceInfo.getMessage());
            }
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/recruitmentDetail";
    }

    /**
     * 招聘官前往招聘详情
     * @param recruitmentInfoId 招聘信息id
     * @param request 请求
     * @param session session
     * @return 视图
     */
    @GetMapping("/interviewer/myRecruitment/detail/id/{recruitmentInfoId}")
    public String toMyDetailRecruitmentInfo(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId,HttpServletRequest request,HttpSession session){
        ResultInfo resultInfo = recruitmentInfoService.queryRecruitmentInfoById(recruitmentInfoId);
        if (resultInfo.getSuccess()) {
            RecruitmentInfo recruitmentInfo = (RecruitmentInfo) resultInfo.getData();
            request.setAttribute("myRecruitmentInfo",recruitmentInfo);
            ResultInfo userServiceInfo = userService.queryUserById(recruitmentInfo.getUserId());
            if(userServiceInfo.getSuccess()){
                User interviewer = (User)userServiceInfo.getData();
                request.setAttribute("interviewer",interviewer);
            }else {
                session.setAttribute("errorMsg",userServiceInfo.getMessage());
            }
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

    @GetMapping("/registrationInfo/signUp/id/{recruitmentInfoId}")
    public String signUp(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId,HttpSession session){
        ResultInfo recruitmentInfoServiceResult = recruitmentInfoService.queryRecruitmentInfoById(recruitmentInfoId);
        if(recruitmentInfoServiceResult.getSuccess()){
            RecruitmentInfo recruitmentInfo = (RecruitmentInfo)recruitmentInfoServiceResult.getData();
            User loginUser = (User)session.getAttribute("loginUser");
            RegistrationInfo registrationInfo = new RegistrationInfo();
            registrationInfo.setRecruitmentInfoId(recruitmentInfoId);
            registrationInfo.setUserId(loginUser.getId());
            ResultInfo registrationInfoServiceResult = registrationInfoService.insertRegistrationInfo(registrationInfo);
            if(!registrationInfoServiceResult.getSuccess()){
                session.setAttribute("errorMsg",registrationInfoServiceResult.getMessage());
            }
        }else {
            session.setAttribute("errorMsg",recruitmentInfoServiceResult.getMessage());
        }
        return "redirect:/recruitment/detail/id/"+recruitmentInfoId;
    }

    @GetMapping({ "/myRegistration","/myRegistration.html"})
    public String toMyRegistration(HttpServletRequest request, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        ResultInfo resultInfo = registrationInfoService.queryRegistrationInfoByUid(loginUser.getId());
        if(resultInfo.getSuccess()){
            List<RegistrationInfo> registrationInfoList = (List<RegistrationInfo>)resultInfo.getData();
            List<RecruitmentInfo> recruitmentInfoList = new ArrayList<RecruitmentInfo>();
            for (RegistrationInfo registrationInfo : registrationInfoList) {
                ResultInfo queryRecruitmentInfoResult = recruitmentInfoService.queryRecruitmentInfoById(registrationInfo.getRecruitmentInfoId());
                if (queryRecruitmentInfoResult.getSuccess()){
                    recruitmentInfoList.add((RecruitmentInfo) queryRecruitmentInfoResult.getData());
                }else {
                    session.setAttribute("errorMsg",resultInfo.getMessage());
                }
            }
            request.setAttribute("myInfoList",recruitmentInfoList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
        }
        return "/myRegistration";
    }
}
