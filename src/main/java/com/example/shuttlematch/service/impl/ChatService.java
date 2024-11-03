package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.entity.Message;
import com.example.shuttlematch.entity.Room;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.request.GetRoomRequest;
import com.example.shuttlematch.entity.request.RoomRequest;
import com.example.shuttlematch.enums.MessageType;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.MessageRequest;
import com.example.shuttlematch.repository.MatchRepository;
import com.example.shuttlematch.repository.MessageRepository;
import com.example.shuttlematch.repository.RoomRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.service.IChatService;
import com.example.shuttlematch.utils.AccountUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService{

    private final MessageRepository messageRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final AccountUtils accountUtils;

    public Room createNewRoom(RoomRequest roomRequest) {
        Set<User> users = new HashSet<>();
        User user1 = userRepository.findUserById(roomRequest.getMembers().get(0));
        User user2 = userRepository.findUserById(roomRequest.getMembers().get(1));
        Room roomCheck = roomRepository.findRoomByUsersIsContainingAndUsersIsContaining(user1,user2);
        if(roomCheck!=null) return roomCheck;

        Room room = new Room();
        room.setUsers(users);
        for (long accountId : roomRequest.getMembers()) {
            try {
                User user = userRepository.findUserById(accountId);
                user.getRooms().add(room);
                users.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return roomRepository.save(room);
    }

    public List<Room> getRoomsByAccountID() {
        User user = accountUtils.getCurrentUser();
        List<Room> rooms = roomRepository.findRoomsByUsersIsContaining(user);
        if (rooms != null) {
            return rooms.stream().sorted(Comparator.comparing(Room::getLastUpdated).reversed()).collect(Collectors.toList());
        }
        return null;
    }

    public Room getRoomDetail(int roomID) {
        Room roomDTO = roomRepository.findRoomByRoomID(roomID);
        if (roomDTO != null)
            roomDTO.setMessages(roomDTO.getMessages().stream().sorted(Comparator.comparing(Message::getCreateAt)).collect(Collectors.toList()));
        return roomDTO;
    }

    public Message sendMessage(MessageRequest messageRequest, int roomId) {
        User user = accountUtils.getCurrentUser();
        Room roomDTO = roomRepository.findRoomByRoomID(roomId);
        Message messageDTO = new Message();
        messageDTO.setUser(user);
        messageDTO.setRoom(roomDTO);
        messageDTO.setMessage(messageRequest.getMessage());
        roomDTO.setLastUpdated(new Date());
        roomDTO.setLastMessage(messageRequest.getMessage());
        roomRepository.save(roomDTO);
        for (User user1 : roomDTO.getUsers()) {
            if (user1.getId() !=(user.getId())) {
                System.out.println("real time");
                System.out.println(user1.getId());
                messagingTemplate.convertAndSend("/topic/chat/" + user1.getId(), "New message");
            }
        }
        return messageRepository.save(messageDTO);
    }

    public void setTyping(int roomID, String name) {
        Room roomDTO = roomRepository.findRoomByRoomID(roomID);
        for (User account : roomDTO.getUsers()) {
            messagingTemplate.convertAndSend("/topic/chat/" + account.getId(), name + " is typing ... ") ;
        }
    }

    public Room getRoom(GetRoomRequest getRoomRequest) {
        User user1 = userRepository.findUserById(getRoomRequest.getUser1());
        User user2 = userRepository.findUserById(getRoomRequest.getUser2());

        Set<User> accountDTOS = new HashSet<>();
        accountDTOS.add(user1);
        accountDTOS.add(user2);

        Room room = roomRepository.findRoomByUsersIsContainingAndUsersIsContaining(user1, user2);
        if (room == null) {
            room = new Room();
            room.setUsers(accountDTOS);
            room.setName("[" + user1.getFullName() + " and "+ user2.getFullName() + "]");
            room = roomRepository.save(room);
        }

        return room;
    }



}
