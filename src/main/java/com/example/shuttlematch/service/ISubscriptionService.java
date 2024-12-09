package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionCreateRequest;
import com.example.shuttlematch.payload.request.SubscriptionUpdateRequest;
import com.example.shuttlematch.payload.response.SubscriptionResponse;

import java.util.List;

public interface ISubscriptionService {
    ApiResponse<List<SubscriptionResponse>> getAll();

    ApiResponse<SubscriptionResponse> createSubscription(SubscriptionCreateRequest request);
    ApiResponse<SubscriptionResponse> updateSubscription(SubscriptionUpdateRequest request);
    ApiResponse<SubscriptionResponse> deleteSubscription(Long id);
}
