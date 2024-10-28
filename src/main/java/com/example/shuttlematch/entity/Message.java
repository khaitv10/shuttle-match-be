package com.example.shuttlematch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
@Accessors(chain=true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;

    String message;

    Date createAt = new Date();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

}
