package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private String receiver;
    private MessageType type;
    private long timestamp;

    public ChatMessage(String content, String sender, String receiver, MessageType type) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
}
