package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.entity.Message;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.MessageType;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.MessageRequest;
import com.example.shuttlematch.repository.MatchRepository;
import com.example.shuttlematch.repository.MessageRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.service.IChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService implements IChatService {

    private final MessageRepository messageRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<String> sendMessage(ChatMessage chatMessage, String senderEmail) {
//        User sender = userRepository.findByEmail(senderEmail).orElseThrow(
//                () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
//        );
//        User receiver = userRepository.findById(chatMessage.getReceiverId()).orElseThrow(
//                () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
//        );
//
//        boolean isMatched = matchRepository.existsByUser1IdAndUser2Id(sender.getId(), receiver.getId()) ||
//                            matchRepository.existsByUser1IdAndUser2Id(receiver.getId(), sender.getId());
//
//        if (!isMatched) {
//            return new ApiResponse<>(ResponseCode.USER_NOT_MATCH, "User not matched");
//        }
//
//        if (chatMessage.getContent() == null || chatMessage.getContent().trim().isEmpty()) {
//            return new ApiResponse<>(ResponseCode.MESSAGE_EMPTY, "Message content cannot be empty");
//        }
//
//        Message message = new Message()
//                .setSender(sender)
//                .setReceiver(receiver)
//                .setContent(chatMessage.getContent())
//                .setType(MessageType.CHAT);
//
//        messageRepository.save(message);
//
//        return new ApiResponse<>(ResponseCode.MESSAGE_SENT_SUCCESS, "Message sent successfully");
        return null;
    }



}
