package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.SwipeType;
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
@Table(name = "match")
@Accessors(chain=true)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id_1", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id_2", referencedColumnName = "id")
    private User user2;

    @Column(name = "match_time")
    private LocalDateTime matchTime;

    @Column(name = "is_active")
    private boolean isActive;
}
