package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private String receiver;
    private String content;
}
