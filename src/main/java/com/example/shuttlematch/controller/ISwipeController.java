package com.example.shuttlematch.controller;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.request.UserRegisterRequest;
import com.example.shuttlematch.payload.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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

}
