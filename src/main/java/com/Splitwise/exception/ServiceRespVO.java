package com.Splitwise.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRespVO {
    private int responseCode;
    private String responseMessage;
    private Object responseData;

    public ServiceRespVO(int responseCode, String responseMessage) {
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ServiceRespVO(int responseCode, Object responseData){
        this.responseCode = responseCode;
        this.responseData=responseData;
    }
    public String toString() {
        return "ServiceRespVO [responseCode=" + responseCode + ", responseMessage=" + responseMessage
                + ", responseData=" + responseData +" ]";
    }
}
