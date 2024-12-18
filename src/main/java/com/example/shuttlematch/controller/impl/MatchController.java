package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IMatchController;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.service.IMatchService;
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
public class MatchController implements IMatchController {

    private final IMatchService matchService;

    @Override
    public ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getAllMatched(Principal principal) {
        log.info("Has a request to get all matched by user: {}", principal.getName());
        ApiResponse<List<UserSummaryResponse>> listResponse = matchService.getAllMatched(principal.getName());
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }
}
