package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.DayInWeek;
import com.example.shuttlematch.enums.Time;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "court")
public class Courts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String location;

    @Column(name = "court_name",nullable = false)
    private String courtName;

    @Column(nullable = false)
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "day_working")
    @Column(name = "day_working")
    private Set<DayInWeek> dayWorking;

}
