package com.example.shuttlematch.payload.common;


import com.example.shuttlematch.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse <T>{

    private int code;
    private String message;
    private T data;


    public ApiResponse(){}

    public ApiResponse(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }


    public ApiResponse error (String ex){
        this.code = ResponseCode.FAILED.getCode();
        this.message = ex;
        this.data = null;
        return this;
    }

}
