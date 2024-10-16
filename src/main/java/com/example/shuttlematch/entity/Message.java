package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    // Thiết lập thời gian gửi trước khi lưu tin nhắn vào DB
    @PrePersist
    protected void onCreate() {
        this.sendTime = LocalDateTime.now();
    }

}
