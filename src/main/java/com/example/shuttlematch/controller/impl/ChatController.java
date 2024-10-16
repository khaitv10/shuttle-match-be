package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IChatController;
import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.service.IChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class ChatController implements IChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final IChatService chatService;

    @Override
    public ResponseEntity<ApiResponse<String>> sendMessage(ChatMessage chatMessage, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(ResponseCode.USER_NOT_FOUND, "User is not authenticated"));
        }

        log.info("Has request to send message with data {}", chatMessage.toString());
        ApiResponse<String> response = chatService.sendMessage(chatMessage, principal.getName());

        if (response.getCode() == ResponseCode.MESSAGE_SENT_SUCCESS.getCode()) {
            try {
                messagingTemplate.convertAndSend("/queue/" + chatMessage.getReceiverId(), chatMessage);
                log.info("Message sent to {} successfully", chatMessage.getReceiverId());
            } catch (Exception e) {
                log.error("Failed to send message to receiver {}", chatMessage.getReceiverId(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(ResponseCode.MESSAGE_SENT_FAIl, "Failed to send message"));
            }
            return ResponseEntity.ok(response);
        } else {
            log.warn("Unable to send message: {}", response.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
