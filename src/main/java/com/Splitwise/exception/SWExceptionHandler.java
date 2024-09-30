package com.Splitwise.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SWExceptionHandler {

    @ExceptionHandler(SWException.class)
    //@ResponseBody
    public ServiceRespVO handleSWException(SWException ex){
        return  new ServiceRespVO(ex.getErrorCode(),ex.getErrorMessage());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    protected ServiceRespVO handleGeneralException(Exception ex) {
//        log.error(ex.getMessage(), ex);
//        slackService.logException(ex,DMSMessages.GENERAL_ERROR_CODE,DMSMessages.GENERAL_ERROR_MESSAGE);
//        return new ServiceRespVO(DMSMessages.GENERAL_ERROR_CODE,DMSMessages.GENERAL_ERROR_MESSAGE);
//    }

}
