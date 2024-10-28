package com.example.shuttlematch.entity.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoomRequest {
    String name;
    List<Long> members;
}
