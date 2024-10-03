package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.SwipeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "swipes")
@Accessors(chain=true)
public class Swipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "id")
    private User toUser;

    @Column(name = "swipe_type")
    @Enumerated(EnumType.STRING)
    private SwipeType swipeType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "swipe_time")
    private LocalDateTime swipeTime;

}
