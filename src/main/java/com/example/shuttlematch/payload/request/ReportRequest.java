package com.example.shuttlematch.payload.request;

import com.example.shuttlematch.enums.Reason;
import com.example.shuttlematch.enums.SwipeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private long toUserId;
    private Reason reason;
}
