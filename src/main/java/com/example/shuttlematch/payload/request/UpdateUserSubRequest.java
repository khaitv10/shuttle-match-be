package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserSubRequest {
    long userId;
    long transactionId;
}
