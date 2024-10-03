package com.example.shuttlematch.controller.impl;


import com.example.shuttlematch.controller.ISwipeController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.service.ISwipeService;
import com.example.shuttlematch.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@Valid
public class SwipeController implements ISwipeController {

    @Autowired
    private IUserService userService;
    private ISwipeService swipeService;

    @Override
    public ResponseEntity<ApiResponse<String>> swipe(SwipeRequest request, Principal principal) {
        //log.info("Has a request to swipe: {}", request.toString());
        //ResponseEntity<ApiResponse<UserResponse>> userResponseApiResponse = userService.register(request);
        return null;
    }
}
