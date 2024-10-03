package com.example.shuttlematch.service;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.*;
import com.example.shuttlematch.payload.response.TokenResponse;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IUserService {


//    String authenticate(LoginDto loginDto);

    ResponseEntity<ApiResponse<UserResponse>> register(UserRegisterRequest request);

    ApiResponse<TokenResponse> login(LoginRequest request);

    ApiResponse<TokenResponse> loginGoogle(LoginGoogleRequest request);

    ApiResponse<String> changePassword(PasswordChangeRequest request, String email);

    ApiResponse<UserResponse> getUserInfo(String email);

//    User saverUser(User user);
//
//    ApiResponse<StatusResponse> checkOtpWhenRegister(CheckOtpWhenRegisterRequest request);

    ApiResponse<UserResponse> updateUserInfo(UserUpdateRequest request);

//    PageDataResponse<UserSummaryResponse> getAllPage(UserGetPageRequest request);

    ApiResponse<List<UserSummaryResponse>> getAll(String email);

    ApiResponse<UserResponse> getInfo(long id);
}
