package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private String content;
    private String sender;
    private String receiver;
    private MessageType type;
    private long timestamp;
}
