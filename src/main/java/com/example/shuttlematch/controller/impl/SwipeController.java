package com.example.shuttlematch.controller.impl;


import com.example.shuttlematch.controller.ISwipeController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.service.ISwipeService;
import com.example.shuttlematch.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class SwipeController implements ISwipeController {

    private final ISwipeService swipeService;

    @Override
    public ResponseEntity<ApiResponse<String>> swipe(SwipeRequest request, Principal principal) {
        log.info("Has a request to swipe: {}", request.toString());
        ApiResponse<String> swipeResponse = swipeService.swipe(request, principal.getName());
        return new ResponseEntity<>(swipeResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getAllLike(Principal principal) {
        log.info("Has a request to swipe: {}", principal.getName());
        ApiResponse<List<UserSummaryResponse>> swipeResponse = swipeService.getAllLike(principal.getName());
        return new ResponseEntity<>(swipeResponse, HttpStatus.OK);
    }

}
