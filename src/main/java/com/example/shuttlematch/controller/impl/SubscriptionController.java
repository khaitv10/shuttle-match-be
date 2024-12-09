package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.ISubscriptionController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionCreateRequest;
import com.example.shuttlematch.payload.request.SubscriptionUpdateRequest;
import com.example.shuttlematch.payload.response.SubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.service.ISubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class SubscriptionController implements ISubscriptionController {

    private final ISubscriptionService subscriptionService;
    @Override
    public ResponseEntity<ApiResponse<List<SubscriptionResponse>>> getAll() {
        ApiResponse<List<SubscriptionResponse>> listApiResponse = subscriptionService.getAll();
        return new ResponseEntity<>(listApiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(SubscriptionCreateRequest request) {
        ApiResponse<SubscriptionResponse> response = subscriptionService.createSubscription(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<SubscriptionResponse>> updateSubscription(SubscriptionUpdateRequest request) {
        ApiResponse<SubscriptionResponse> response = subscriptionService.updateSubscription(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<SubscriptionResponse>> inactiveSubscription(Long id) {
        ApiResponse<SubscriptionResponse> response = subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
