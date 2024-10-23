package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReasonResponse {
    private String name;
    private String reason;

    public ReasonResponse(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

}
