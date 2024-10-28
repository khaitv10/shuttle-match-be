package com.example.shuttlematch.entity.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GetRoomRequest {
    long user1;
    long user2;
}
