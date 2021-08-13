package com.oujiajun.recruitment.controller;

import com.oujiajun.recruitment.entity.dto.PageBean;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.InterviewPeriod;
import com.oujiajun.recruitment.entity.po.RecruitmentInfo;
import com.oujiajun.recruitment.entity.po.RegistrationInfo;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.RecruitmentInfoPage;
import com.oujiajun.recruitment.entity.vo.UserRegistrationInfo;
import com.oujiajun.recruitment.service.InterviewPeriodService;
import com.oujiajun.recruitment.service.RecruitmentInfoService;
import com.oujiajun.recruitment.service.RegistrationInfoService;
import com.oujiajun.recruitment.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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

    @Autowired
    InterviewPeriodService interviewPeriodService;

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

    @GetMapping({ "/registrationInfo/show/allApplicants/id/{recruitmentInfoId}"})
    public String toApplicants(@PathVariable("recruitmentInfoId")Integer recruitmentInfoId, HttpServletRequest request, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        ResultInfo resultInfo = registrationInfoService.queryUserRegistrationInfoForInterviewer(recruitmentInfoId);
        if(resultInfo.getSuccess()){
            List<UserRegistrationInfo> userRegistrationInfoList = (List<UserRegistrationInfo>)resultInfo.getData();
            request.setAttribute("infoList",userRegistrationInfoList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "redirect:/interviewer/myRecruitment/detail/id/" + recruitmentInfoId;
        }
        return "/interviewer/applicants";
    }

    @GetMapping("/interviewer/registration/pass/id/{registrationInfoId}")
    public String passRegistration(@PathVariable("registrationInfoId")Integer registrationInfoId, HttpServletRequest request, HttpSession session){
        ResultInfo resultInfo = registrationInfoService.passRegistration(registrationInfoId);
        if(!resultInfo.getSuccess()){
            session.setAttribute("errorMsg", resultInfo.getMessage());
        }
        ResultInfo queryResult = registrationInfoService.queryRegistrationInfoById(registrationInfoId);
        Integer recruitmentInfoId = null;
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg", resultInfo.getMessage());
        }else {
            recruitmentInfoId = ((RegistrationInfo)queryResult.getData()).getRecruitmentInfoId();
        }
        return "redirect:/registrationInfo/show/allApplicants/id/" + recruitmentInfoId;
    }

    @GetMapping("/interviewer/registration/out/id/{registrationInfoId}")
    public String passOutRegistration(@PathVariable("registrationInfoId")Integer registrationInfoId, HttpServletRequest request, HttpSession session){
        ResultInfo resultInfo = registrationInfoService.passOutRegistration(registrationInfoId);
        if(!resultInfo.getSuccess()){
            session.setAttribute("errorMsg", resultInfo.getMessage());
        }
        ResultInfo queryResult = registrationInfoService.queryRegistrationInfoById(registrationInfoId);
        Integer recruitmentInfoId = null;
        if (!resultInfo.getSuccess()){
            session.setAttribute("errorMsg", resultInfo.getMessage());
        }else {
            recruitmentInfoId = ((RegistrationInfo)queryResult.getData()).getRecruitmentInfoId();
        }
        return "redirect:/registrationInfo/show/allApplicants/id/" + recruitmentInfoId;
    }

    @GetMapping("/registrationInfo/chooseDate/id/{recruitmentInfoId}")
    public String toChooseDate(@PathVariable("recruitmentInfoId") Integer recruitmentInfoId, HttpServletRequest request,HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        ResultInfo resultInfo = recruitmentInfoService.queryInterviewPeriodByRecruitmentInfoId(recruitmentInfoId);
        if (resultInfo.getSuccess()) {
            List<InterviewPeriod> periodList = (List<InterviewPeriod>) resultInfo.getData();
            request.setAttribute("periodList",periodList);
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "redirect:/recruitment/detail/id/" + recruitmentInfoId;
        }
        return "/chooseDate";
    }

    @PostMapping("/interview/chooseDate")
    public String chooseDate(
            @RequestParam("interviewPeriodId")Integer interviewPeriodId,
            HttpServletRequest request,
            HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        ResultInfo resultInfo = recruitmentInfoService.queryInterviewPeriodByInterviewPeriodId(interviewPeriodId);
        if (resultInfo.getSuccess()) {
            InterviewPeriod interviewPeriod = (InterviewPeriod)resultInfo.getData();
            ResultInfo registrationInfoResult = registrationInfoService.queryRegistrationInfoByUidAndRid(loginUser.getId(),interviewPeriod.getRecruitmentInfoId());
            if (registrationInfoResult.getSuccess()){
                RegistrationInfo registrationInfo = (RegistrationInfo) registrationInfoResult.getData();
                ResultInfo insertResult = registrationInfoService.insertInterviewRegistrationInfo(interviewPeriodId,registrationInfo.getRegistrationInfoId());
                if (insertResult.getSuccess()){
                    return "redirect:/recruitment/detail/id/" + interviewPeriod.getRecruitmentInfoId();
                }else {
                    session.setAttribute("errorMsg",insertResult.getMessage());
                    return "redirect:/registrationInfo/chooseDate/id/" + interviewPeriod.getRecruitmentInfoId();
                }
            }else {
                session.setAttribute("errorMsg",registrationInfoResult.getMessage());
                return "redirect:/registrationInfo/chooseDate/id/" + interviewPeriod.getRecruitmentInfoId();
            }
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "redirect:/myRegistration";
        }
    }


    /**
     * 排队面试队伍
     */
    static Map<Integer,LinkedList<UserRegistrationInfo>> interviewPeriodListMap = new HashMap<Integer,LinkedList<UserRegistrationInfo>>();

    @GetMapping("/registrationInfo/lineUp/id/{registrationInfoId}")
    public String lineUp(
            @PathVariable("registrationInfoId") Integer registrationInfoId,
            HttpServletRequest request,
             HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            request.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        ResultInfo queryRegistrationInfoResult = registrationInfoService.queryRegistrationInfoById(registrationInfoId);
        if (!queryRegistrationInfoResult.getSuccess()){
            session.setAttribute("errorMsg",queryRegistrationInfoResult.getData()+" 面试排队失败");
            return "redirect:/myRegistration";
        }
        RegistrationInfo registrationInfo = (RegistrationInfo) queryRegistrationInfoResult.getData();
        ResultInfo queryInterviewPeriodResult = interviewPeriodService.queryInterviewPeriodByRegistrationInfoId(registrationInfoId);
        if (!queryInterviewPeriodResult.getSuccess()){
            session.setAttribute("errorMsg",queryInterviewPeriodResult.getData()+" 面试排队失败");
            return "redirect:/recruitment/detail/id/" + registrationInfo.getRecruitmentInfoId();
        }
        InterviewPeriod interviewPeriod = (InterviewPeriod) queryInterviewPeriodResult.getData();
        // 进行排队
        int beforeLineUpNumber;
        UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo(registrationInfo,loginUser);
        List<UserRegistrationInfo> userRegistrationInfoList = interviewPeriodListMap.get(interviewPeriod.getInterviewPeriodId());
        if (userRegistrationInfoList == null){
            // 创建一个新的
            LinkedList<UserRegistrationInfo> userRegistrationInfos = new LinkedList<>();
            userRegistrationInfos.add(userRegistrationInfo);
            interviewPeriodListMap.put(interviewPeriod.getInterviewPeriodId(),userRegistrationInfos);
            beforeLineUpNumber = 0;
        }else {
            if (userRegistrationInfoList.stream().anyMatch(userRegistrationInfo::equals)) {
                beforeLineUpNumber = 0;
                for (UserRegistrationInfo info : userRegistrationInfoList) {
                    if(!info.equals(userRegistrationInfo)){
                        beforeLineUpNumber++;
                    }else {
                        break;
                    }
                }
            }else {
                beforeLineUpNumber = userRegistrationInfoList.size();
                userRegistrationInfoList.add(userRegistrationInfo);
            }
        }
        request.setAttribute("beforeLineUpNumber",beforeLineUpNumber);
        return "/lineUp";
    }

    @GetMapping("/registrationInfo/start/interview/id/{recruitmentInfoId}")
    public String chooseInterviewPeriod(
            @PathVariable("recruitmentInfoId") Integer recruitmentInfoId,
            HttpServletRequest request,
            HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            session.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        //查找招聘信息对应的面试时间段
        ResultInfo resultInfo = recruitmentInfoService.queryInterviewPeriodByRecruitmentInfoId(recruitmentInfoId);
        if (resultInfo.getSuccess()) {
            List<InterviewPeriod> periodList = (List<InterviewPeriod>) resultInfo.getData();
            request.setAttribute("periodList", periodList);
            return "/interviewer/choosePeriod";
        }else {
            session.setAttribute("errorMsg",resultInfo.getMessage());
            return "redirect:/interviewer/myRecruitment/detail/id/" + recruitmentInfoId;
        }
    }

    @PostMapping("/interview/startInterview")
    public String startInterview(
            @RequestParam("interviewPeriodId")int interviewPeriodId,
            HttpServletRequest request,
            HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            session.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        List<UserRegistrationInfo> userRegistrationInfoList = interviewPeriodListMap.get(interviewPeriodId);
        if (userRegistrationInfoList == null) {
            // 创建一个新的
            LinkedList<UserRegistrationInfo> userRegistrationInfos = new LinkedList<>();
            interviewPeriodListMap.put(interviewPeriodId,userRegistrationInfos);
            request.setAttribute("userRegistrationInfoList",userRegistrationInfos);
        }else {
            request.setAttribute("userRegistrationInfoList",userRegistrationInfoList);
            if (userRegistrationInfoList.size() > 0){
                request.setAttribute("currentUser",userRegistrationInfoList.get(0));
            }
        }
        return "/interviewer/interview";
    }

    @GetMapping("/interview/startInterview/id/{interviewPeriodId}")
    public String startInterview2(
            @PathVariable("interviewPeriodId")int interviewPeriodId,
            HttpServletRequest request,
            HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser == null){
            session.setAttribute("errorMsg","请登陆后进行该操作");
            return "redirect:/login";
        }
        List<UserRegistrationInfo> userRegistrationInfoList = interviewPeriodListMap.get(interviewPeriodId);
        if (userRegistrationInfoList == null) {
            // 创建一个新的
            LinkedList<UserRegistrationInfo> userRegistrationInfos = new LinkedList<>();
            interviewPeriodListMap.put(interviewPeriodId,userRegistrationInfos);
            request.setAttribute("userRegistrationInfoList",userRegistrationInfos);
        }else {
            request.setAttribute("userRegistrationInfoList",userRegistrationInfoList);
            if (userRegistrationInfoList.size() > 0){
                request.setAttribute("currentUser",userRegistrationInfoList.get(0));
            }
        }
        return "/interviewer/interview";
    }

    @GetMapping("/next/{registrationInfoId}")
    public String next(
            @PathVariable("registrationInfoId") Integer registrationInfoId,
            HttpServletRequest request,
            HttpSession session
            ){
        ResultInfo queryUserRegistrationInfoResult = registrationInfoService.queryUserRegistrationInfoByRegistrationInfoId(registrationInfoId);
        if (!queryUserRegistrationInfoResult.getSuccess()){
            return "redirect:/index";
        }
        UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) queryUserRegistrationInfoResult.getData();
        ResultInfo queryInterviewPeriodResult = interviewPeriodService.queryInterviewPeriodByRegistrationInfoId(registrationInfoId);
        if (!queryInterviewPeriodResult.getSuccess()){
            session.setAttribute("errorMsg","步过失败");
            return "redirect:/registrationInfo/start/interview/id/" + userRegistrationInfo.getRecruitmentInfoId();
        }
        InterviewPeriod interviewPeriod = (InterviewPeriod) queryInterviewPeriodResult.getData();
        LinkedList<UserRegistrationInfo> userRegistrationInfoList = interviewPeriodListMap.get(interviewPeriod.getInterviewPeriodId());
        System.out.println(userRegistrationInfo);
        System.out.println("qian"+userRegistrationInfoList);
        userRegistrationInfoList.remove(userRegistrationInfo);
        System.out.println("hou"+userRegistrationInfoList);
        return "redirect:/interview/startInterview/id/" + interviewPeriod.getInterviewPeriodId();
    }

    @GetMapping(value = {
            "/select/city/{city}/company/{company}/salary/{smallSalary}/{bigSalary}/page/{page}",
            "/select/city/{city}/salary/{smallSalary}/{bigSalary}/page/{page}",
            "/select/city/{city}/company/{company}/page/{page}",
            "/select/company/{company}/salary/{smallSalary}/{bigSalary}/page/{page}",
            "/select/city/{city}/page/{page}",
            "/select/company/{company}/page/{page}",
            "/select/salary/{smallSalary}/{bigSalary}/page/{page}",
            "/select/page/{page}"},produces = "text/html;charset=UTF-8")
    public String queryRecruitmentInfoPageWithMultiCondition(
            @PathVariable(value = "company", required = false)String company,
            @PathVariable(value = "city",required = false)String workCity,
            @PathVariable(value = "page",required = false)Integer currentPage,
            @PathVariable(value = "bigSalary", required = false)Integer bigSalary,
            @PathVariable(value = "smallSalary",required = false)Integer smallSalary,
            HttpServletRequest request,
            HttpSession session) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<>();
        if (currentPage != null){
            params.put("currentPage",currentPage);
            request.setAttribute("currentPage", currentPage);
        }
        if (workCity != null){
            params.put("workCity",workCity);
            request.setAttribute("workCity",workCity);
        }
        if (company != null){
            params.put("company",company);
            request.setAttribute("company",company);
        }
        if (bigSalary != null){
            params.put("bigSalary",bigSalary);
            request.setAttribute("bigSalary",bigSalary);
        }
        if (smallSalary != null){
            params.put("smallSalary",smallSalary);
            request.setAttribute("smallSalary",smallSalary);
        }
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        ResultInfo queryResult = recruitmentInfoService.queryRecruitmentInfoForPage(pageBean,params);
        if (!queryResult.getSuccess()){
            session.setAttribute("errorMsg",queryResult.getMessage());
        }
        RecruitmentInfoPage recruitmentInfoPage = (RecruitmentInfoPage) queryResult.getData();
        request.setAttribute("recruitmentInfoPage",recruitmentInfoPage);
        return "/selectRecruitment";
    }
}
