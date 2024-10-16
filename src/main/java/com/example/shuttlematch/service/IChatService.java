package com.example.shuttlematch.service;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.MessageRequest;
import com.example.shuttlematch.payload.response.MessageResponse;

import java.util.List;

public interface IChatService {
    ApiResponse<String> sendMessage(ChatMessage chatMessage, String sender);
    //ApiResponse<List<MessageResponse>> getMessageHistory(String sender, String receiver);
}
