package com.example.shuttlematch.service;

import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.TransactionResponse;

public interface ITransactionService {
    long createUserSubscriptionTransaction(String email, long subscriptionId);

    String getPaymentUrl(long transactionId, String redirectUrl);

    ApiResponse<TransactionResponse> updateTransaction(long transactionId);
}
