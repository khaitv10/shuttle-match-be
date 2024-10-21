package com.example.shuttlematch.controller;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.SubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
