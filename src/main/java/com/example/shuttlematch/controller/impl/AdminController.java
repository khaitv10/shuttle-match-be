package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IAdminController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.AllAccountResponse;
import com.example.shuttlematch.payload.response.AllPaymentResponse;
import com.example.shuttlematch.service.impl.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class AdminController implements IAdminController {
    private final AdminService adminService;
    @Override
    public ResponseEntity<ApiResponse<AllAccountResponse>> getAllAccount(Principal principal, int currentPage, int size) {
        ApiResponse<AllAccountResponse> response = adminService.getAllAccount(principal.getName(), currentPage, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<AllPaymentResponse>> getAllPayment(Principal principal, int currentPage, int size) {
        ApiResponse<AllPaymentResponse> response = adminService.getAllPayment(principal.getName(), currentPage, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
