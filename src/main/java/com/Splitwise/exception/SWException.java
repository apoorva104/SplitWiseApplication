package com.Splitwise.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SWException extends RuntimeException {
    private int errorCode;
    private String errorMessage;
    @Override
    public String getMessage(){
        return "ErrorCode: "+errorCode+" ErrorMessage: "+errorMessage;
    }
}
