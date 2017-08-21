package com.qatang.team.manager.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jinsheng
 * @since 2016-04-28 09:49
 */
@ControllerAdvice
public class WebExceptionHandler {
    
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(UnauthorizedException.class)
    public RedirectView handleUnauthorizedException(HttpServletRequest request) {
        RedirectView redirectView = new RedirectView("/unauthorized");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        String unauthorizedUrl = "";
        if (outputFlashMap != null){
            StringBuffer requestUrl = request.getRequestURL();
            outputFlashMap.put("url", requestUrl);
            if (requestUrl != null) {
                unauthorizedUrl = requestUrl.toString();
            }
        }
        logger.error(String.format("未授权异常unauthorizedUrl=[%s]", unauthorizedUrl));
        return redirectView;
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(HttpServletRequest request, Exception exception) {
        RedirectView redirectView = new RedirectView("/error");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("errorMessage", exception.getMessage());
            outputFlashMap.put("ex", exception);
            outputFlashMap.put("url", request.getRequestURL());
        }
        logger.error(exception.getMessage(), exception);
        return redirectView;
    }

//    @ExceptionHandler(RpcException.class)
//    public ModelAndView handleException(RpcException e) {
//        ModelAndView modelAndView = new ModelAndView("/error");
//        modelAndView.addObject("errorMessage", e.getMessage());
//        logger.error(e.getMessage(), e);
//        return modelAndView;
//    }
}
