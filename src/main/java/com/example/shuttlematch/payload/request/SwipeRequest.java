package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwipeRequest {
    private long toUserId;
    private String swipeType;
}
