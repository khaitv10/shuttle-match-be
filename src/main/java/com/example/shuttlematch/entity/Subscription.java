package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "subscriptions")
@Accessors(chain=true)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @Column(name = "duration_days", nullable = false)
    private int durationDays;

    @Column(name = "likes_per_day", nullable = false)
    private int likesPerDay;

    @Enumerated(EnumType.STRING)
    private Status status;

}
