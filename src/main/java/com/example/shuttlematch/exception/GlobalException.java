package com.example.shuttlematch.exception;

import com.example.shuttlematch.payload.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.error(ex.getMessage());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getResponseCode(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(UnauthorizeException.class)
//    @ResponseBody
//    public ResponseEntity<?> handleUnauthorizeException(UnauthorizeException ex) {
//        ApiResponse  apiResponse = new ApiResponse();
//        apiResponse.setMessage(ex.getMessage());
//        apiResponse.setStatus(StatusEnum.ERROR);
//        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
//    }

}
