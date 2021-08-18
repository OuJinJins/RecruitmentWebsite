package com.oujiajun.recruitment.exception;

import com.oujiajun.recruitment.entity.dto.Result;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author oujiajun
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req 请求
     * @param e 异常
     * @return 结果集
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ModelAndView bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("发生业务异常！原因是:",e.getErrorMsg());
        Result result = new Result(e.getErrorCode(),e.getErrorMsg());
        ModelAndView modelAndView = new ModelAndView("/error/5xx");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    /**
     * 处理招聘未审核异常
     * @param req 请求
     * @param e 异常
     * @return 结果集
     */
    @ExceptionHandler(value = NotApprovedException.class)
    @ResponseBody
    public ModelAndView notApprovedExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("发生notApprovedExceptionHandler！原因是:",e.getErrorMsg());
        Result result = new Result(e.getErrorCode(),e.getErrorMsg());
        ModelAndView modelAndView = new ModelAndView("/error/4xx");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    /**
     * 处理招聘未审核异常
     * @param req 请求
     * @param e 异常
     * @return 结果集
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public ModelAndView notLoginExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("发生未登陆异常原因是:",e.getErrorMsg());
        Result result = new Result(e.getErrorCode(),"请登陆后再执行此操作");
        ModelAndView modelAndView = new ModelAndView("/error/4xx");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    /**
     * 处理空指针的异常
     * @param req 请求
     * @param e 异常
     * @return 结果集
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ModelAndView exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        Result result = new Result("500","请求格式不符");
        ModelAndView modelAndView = new ModelAndView("/error/5xx");
        modelAndView.addObject("result",result);
        return modelAndView;
    }


    /**
     * 处理其他异常
     * @param req 请求
     * @param e 异常
     * @return 结果集
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ModelAndView exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        Result result = new Result("500","服务器内部错误");
        ModelAndView modelAndView = new ModelAndView("/error/5xx");
        modelAndView.addObject("result",result);
        return modelAndView;
    }
}
