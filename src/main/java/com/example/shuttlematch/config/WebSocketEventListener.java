package com.example.shuttlematch.config;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .senderId(null) // Đặt senderId là null vì người dùng đã rời đi
                    .receiverId(null) // Điều chỉnh receiverId nếu cần
                    .content(username + " has left the chat")
                    .build();

            // Gửi thông báo rằng người dùng đã rời khỏi phòng chat
            messageOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}

