package com.example.shuttlematch.controller;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.request.UpdateUserSubRequest;
import com.example.shuttlematch.payload.response.UserSubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Tag(name = "UserSubscription controller")
@RequestMapping("/user-subscription")
@Validated
@Valid
public interface IUserSubscriptionController {
    @Operation(
            summary = "Update user subscription after payment success"
    )
    @PutMapping("/v1/updateUserSubscription")
    ResponseEntity<ApiResponse<UserSubscriptionResponse>> updateUserSubscription(@Valid @RequestBody UpdateUserSubRequest request);


}










