package com.example.shuttlematch.controller;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.AllAccountResponse;
import com.example.shuttlematch.payload.response.AllPaymentResponse;
import com.example.shuttlematch.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Tag(name = "Admin controller")
@RequestMapping("/admin")
@Validated
@Valid
public interface IAdminController {
    @Operation(
            summary = "Get all account"
    )
    @GetMapping("/v1/getAllAccount")
    ResponseEntity<ApiResponse<AllAccountResponse>> getAllAccount(Principal principal,
                                                                  @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
                                                                  @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size);

    @Operation(
            summary = "Get all payment"
    )
    @GetMapping("/v1/getAllPayment")
    ResponseEntity<ApiResponse<AllPaymentResponse>> getAllPayment(Principal principal,
                                                                  @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
                                                                  @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size);
}

