package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionCreateRequest;
import com.example.shuttlematch.payload.response.AllAccountResponse;
import com.example.shuttlematch.payload.response.AllPaymentResponse;
import com.example.shuttlematch.payload.response.SubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface IAdminService {
    ApiResponse<AllAccountResponse> getAllAccount(String email, int currentPage, int size);

    ApiResponse<AllPaymentResponse> getAllPayment(String email, int currentPage, int size);

}
