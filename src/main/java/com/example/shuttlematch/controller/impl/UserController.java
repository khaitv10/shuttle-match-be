package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IUserController;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.*;
import com.example.shuttlematch.payload.response.TokenResponse;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@Valid
public class UserController implements IUserController {


    @Autowired
    private IUserService userService;

    // TODO: B1. Gui form dank ky.
    @Override
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(UserRegisterRequest request, BindingResult result) {
        log.info("Has a request to register: {}", request.toString());
        log.info("Has validate fail: {}", result);
        ResponseEntity<ApiResponse<UserResponse>> userResponseApiResponse = userService.register(request);
        return userResponseApiResponse;
    }

//    //TODO: B2. Check OTP
//    @Override
//    public ResponseEntity<ApiResponse<StatusResponse>> checkOtpWhenRegister(CheckOtpWhenRegisterRequest request) {
//        log.info("has a request to check OTP: {}", request.toString());
//        ApiResponse<StatusResponse> checked = userService.checkOtpWhenRegister(request);
//        return new ResponseEntity<>(checked, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest request) {
        log.info("has a request to login: {}", request.toString());
        ApiResponse<TokenResponse> loginResponse = userService.login(request);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<TokenResponse>> loginGoogle(LoginGoogleRequest request) {
        log.info("has a request to login: {}", request.toString());
        ApiResponse<TokenResponse> loginResponse = userService.loginGoogle(request);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(Principal principal, PasswordChangeRequest request) {
        log.info("has a request to change password: {}", request.toString());
        ApiResponse<String> response = userService.changePassword(request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> getInfo(Principal principal) {
        log.info("has a request to get info: {}", principal.getName());
        ApiResponse<UserResponse> info = userService.getInfo(principal.getName());
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(Principal principal, UserUpdateRequest request) {
        log.info("has a request to update: {}", principal.getName());
        ApiResponse<UserResponse> userResponse = userService.updateUserInfo(request);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
//
//    @Override
//    public ResponseEntity<ApiResponse<PageDataResponse<UserSummaryResponse>>> getPageAll(Principal principal, UserGetPageRequest request) {
//        log.info("has a request with data: {}", request.toString());
//        PageDataResponse<UserSummaryResponse> userSummaryResponse = userService.getAllPage(request);
//        return new ResponseEntity<>(new ApiResponse<>(ResponseCode.SUCCESS,userSummaryResponse),HttpStatus.OK);
//    }
}