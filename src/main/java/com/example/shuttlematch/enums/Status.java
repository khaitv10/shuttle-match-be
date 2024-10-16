package com.example.shuttlematch.enums;

import lombok.Getter;

@Getter
public enum Status {

    ACTIVE,
    INACTIVE,
    BANNED,

    PENDING,
    MATCHED,

    COMPLETED,
    FAILED,
    CANCELED
    
}
