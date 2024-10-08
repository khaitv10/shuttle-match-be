package com.example.shuttlematch.controller;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.request.UserRegisterRequest;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Tag(name = "Swipe controller")
@RequestMapping("/swipe")
@Validated
@Valid
public interface ISwipeController {

    @Operation(
            summary = "Swipe"
    )
    @PostMapping("/v1/swipe")
    ResponseEntity<ApiResponse<String>> swipe(@Valid @RequestBody SwipeRequest request, Principal principal);

    @Operation(
            summary = "Get all people who like"
    )
    @GetMapping("/v1/getAllLike")
    ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getAllLike(Principal principal);

}
