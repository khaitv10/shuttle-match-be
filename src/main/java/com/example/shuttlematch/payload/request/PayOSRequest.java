package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayOSRequest {
    private int orderId;
    private int paymentId;
    private double amount;
    private String subName;
    private String redirectUrl;
    private String cancelUrl;
    private LocalDateTime createdDate;

    public PayOSRequest(int orderId, double amount, String subName, String redirectUrl, String cancelUrl) {
        this.orderId = orderId;
        this.amount = amount;
        this.subName = subName;
        this.redirectUrl = redirectUrl;
        this.cancelUrl = cancelUrl;
    }
}

