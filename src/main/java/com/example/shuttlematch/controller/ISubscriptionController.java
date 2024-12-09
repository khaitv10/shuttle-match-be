package com.example.shuttlematch.controller;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionCreateRequest;
import com.example.shuttlematch.payload.request.SubscriptionUpdateRequest;
import com.example.shuttlematch.payload.response.SubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Subscription controller")
@RequestMapping("/subscription")
@Validated
@Valid
public interface ISubscriptionController {
    @Operation(
            summary = "Get all subscription"
    )
    @GetMapping("/v1/getAll")
    ResponseEntity<ApiResponse<List<SubscriptionResponse>>> getAll();

    @Operation(
            summary = "Create new subscription"
    )
    @PostMapping("/v1/createSubscription")
    ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(@Valid @RequestBody SubscriptionCreateRequest request);

    @Operation(
            summary = "Update subscription"
    )
    @PutMapping("/v1/updateSubscription")
    ResponseEntity<ApiResponse<SubscriptionResponse>> updateSubscription(@Valid @RequestBody SubscriptionUpdateRequest request);

    @Operation(
            summary = "Inactive subscription"
    )
    @DeleteMapping("/v1/inactiveSubscription/{id}")
    ResponseEntity<ApiResponse<SubscriptionResponse>> inactiveSubscription(@PathVariable Long id);
}
