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
    private Long senderId;
    private Long receiverId;
    private MessageType type;
    private long timestamp;

    public ChatMessage(String content, Long senderId, Long receiverId, MessageType type) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.timestamp = System.currentTimeMillis(); // Tự động set timestamp khi khởi tạo
    }
}
