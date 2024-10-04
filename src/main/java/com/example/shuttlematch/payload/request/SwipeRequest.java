package com.example.shuttlematch.payload.request;

import com.example.shuttlematch.enums.SwipeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwipeRequest {
    private long toUserId;
    private SwipeType swipeType;
}
