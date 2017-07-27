package com.qatang.team.data.controller;

import com.qatang.team.core.bean.ErrorInfo;
import com.qatang.team.core.exception.ClientException;
import com.qatang.team.data.exception.DataClientExceptionLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangzhiliang
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest request, ClientException exception) {
        ErrorInfo r = new ErrorInfo();
        r.setModule(DataClientExceptionLoader.MODULE);
        r.setMessage(exception.getMessage());
        r.setCode(exception.getErrorCode());
        r.setUrl(request.getRequestURL().toString());
        return r;
    }
}
