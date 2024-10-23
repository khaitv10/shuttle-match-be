package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllPaymentResponse {
    private long revenue;
    private List<TransactionResponse> transactionResponseList;

    public AllPaymentResponse(long revenue, List<TransactionResponse> transactionResponseList) {
        this.revenue = revenue;
        this.transactionResponseList = transactionResponseList;
    }
}
