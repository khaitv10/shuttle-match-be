package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.SubscriptionResponse;

import java.util.List;

public interface ISubscriptionService {
    ApiResponse<List<SubscriptionResponse>> getAll();
}
