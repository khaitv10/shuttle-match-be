package com.example.shuttlematch.service;

import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.TransactionResponse;
import com.example.shuttlematch.payload.response.UserSubscriptionResponse;

public interface IUserSubscriptionService {
    ApiResponse<UserSubscriptionResponse> updateUserSubscription(long userId, long transactionId);
}
