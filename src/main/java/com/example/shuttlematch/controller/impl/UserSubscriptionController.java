package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IUserSubscriptionController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.UpdateUserSubRequest;
import com.example.shuttlematch.payload.response.UserSubscriptionResponse;
import com.example.shuttlematch.service.impl.UserSubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class UserSubscriptionController implements IUserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;
    @Override
    public ResponseEntity<ApiResponse<UserSubscriptionResponse>> updateUserSubscription(UpdateUserSubRequest request) {
        log.info("Has a request to update user subscription: {}", request.toString());
        ApiResponse<UserSubscriptionResponse> response = userSubscriptionService.updateUserSubscription(request.getUserId(), request.getTransactionId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
