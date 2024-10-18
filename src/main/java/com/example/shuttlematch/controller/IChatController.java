package com.example.shuttlematch.controller;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.MessageRequest;
import com.example.shuttlematch.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

//@Tag(name = "Chat controller")
//@RequestMapping("/chat")
//@Validated
//public interface IChatController {
//    @Operation(
//            summary = "Send a message"
//    )
//    @MessageMapping("/v1/chat-send")
//    ResponseEntity<ApiResponse<String>> sendMessage(@Payload ChatMessage chatMessage, Principal principal);
//
//}
