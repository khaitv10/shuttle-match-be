package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private long paymentId;
    private String paymentUrl;
    private String userName;

}
