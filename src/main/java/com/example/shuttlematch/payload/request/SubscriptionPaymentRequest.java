package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPaymentRequest {
    private long subscriptionId;
    private String redirectUrl;
}
