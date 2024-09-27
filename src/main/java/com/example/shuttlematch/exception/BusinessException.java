package com.example.shuttlematch.exception;

import com.example.shuttlematch.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BusinessException extends RuntimeException{
    private ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode){
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

}
