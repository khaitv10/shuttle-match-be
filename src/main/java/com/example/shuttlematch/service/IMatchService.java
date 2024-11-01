package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;

import java.util.List;

public interface IMatchService {
    ApiResponse<List<UserSummaryResponse>> getAllMatched(String email);
}
