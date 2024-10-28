package com.example.shuttlematch.controller;


import com.example.shuttlematch.entity.Room;
import com.example.shuttlematch.entity.request.GetRoomRequest;
import com.example.shuttlematch.entity.request.RoomRequest;
import com.example.shuttlematch.payload.request.MessageRequest;
import com.example.shuttlematch.service.impl.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping()
    public ResponseEntity createNewChat(@RequestBody RoomRequest roomRequest) {
        Room room = chatService.createNewRoom(roomRequest);
        return ResponseEntity.ok(room);
    }

    @GetMapping()
    public ResponseEntity getChatByAccountID() {
        List<Room> rooms = chatService.getRoomsByAccountID();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/detail/{roomID}")
    public ResponseEntity getChatDetail(@PathVariable int roomID) {
        return ResponseEntity.ok(chatService.getRoomDetail(roomID));
    }

    @PostMapping("/send/{roomID}")
    public ResponseEntity sendMessage(@PathVariable int roomID, @RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(chatService.sendMessage(messageRequest,roomID));
    }

    @PostMapping("/typing/{roomID}/{name}")
    public void typingMessage(@PathVariable int roomID, @PathVariable String name) {
        chatService.setTyping(roomID, name);
    }

    @PostMapping("/room")
    public ResponseEntity<Room> getRoom(@RequestBody GetRoomRequest getRoomRequest) {
        return ResponseEntity.ok(chatService.getRoom(getRoomRequest));
    }
}
