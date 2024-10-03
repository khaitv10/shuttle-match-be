package com.example.shuttlematch.controller;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.*;
import com.example.shuttlematch.payload.response.TokenResponse;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Tag(name = "User controller")
@RequestMapping("/user")
@Validated
@Valid
public interface IUserController {

    @Operation(
        summary = "Register"
    )
    @PostMapping("/v1/register")
    ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody UserRegisterRequest request, BindingResult result);


//
//    @Operation(
//            summary = "Verify OTP When Register"
//    )
//    @PostMapping("/v1/checkOtp/register")
//    ResponseEntity<ApiResponse<StatusResponse>> checkOtpWhenRegister(@RequestBody CheckOtpWhenRegisterRequest request);


    @Operation(
        summary = "Login"
    )
    @PostMapping("/v1/login")
    ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request);

    @Operation(
            summary = "Login Google"
    )
    @PostMapping("/v1/login-google")
    ResponseEntity<ApiResponse<TokenResponse>> loginGoogle(@RequestBody LoginGoogleRequest request);

    @Operation(
            summary = "Change password"
    )
    @PostMapping("/v1/change-password")
    ResponseEntity<ApiResponse<String>> changePassword(@Valid Principal principal, @RequestBody PasswordChangeRequest request);


    @Operation(
        summary = "Get User Information"
    )
    @GetMapping("/v1/getUserInfo")
    ResponseEntity<ApiResponse<UserResponse>> getUserInfo(Principal principal);



    @Operation(
            summary = "Update info"
    )
    @PutMapping("/v1/update")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(Principal principal, @RequestBody UserUpdateRequest request);



    @Operation(
            summary = "Get all"
    )
    @GetMapping("/v1/getAll")
    ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getAll(Principal principal);

    @Operation(
            summary = "Get Information"
    )
    @GetMapping("/v1/getInfo/{id}")
    ResponseEntity<ApiResponse<UserResponse>> getInfo(Long id);
}
