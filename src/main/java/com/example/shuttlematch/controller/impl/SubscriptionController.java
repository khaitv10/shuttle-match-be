package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.ISubscriptionController;
import com.example.shuttlematch.payload.common.ApiResponse;
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
}
