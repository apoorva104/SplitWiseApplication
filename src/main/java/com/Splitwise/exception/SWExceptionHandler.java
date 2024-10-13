package com.Splitwise.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @ExceptionHandler(SWException.class)
//    public ResponseEntity<ServiceRespVO> handleSWException(SWException ex) {
//        ServiceRespVO response = new ServiceRespVO(ex.getErrorCode(), ex.getErrorMessage());
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);  // Return 401 for JWT token errors
//    }



}
