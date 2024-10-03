package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.PasswordChangeRequest;
import com.example.shuttlematch.payload.request.SwipeRequest;

public interface ISwipeService {

    ApiResponse<String> swipe(SwipeRequest request, String email);
}
