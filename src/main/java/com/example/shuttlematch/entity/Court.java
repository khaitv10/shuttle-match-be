package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.DayInWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courts")
public class Court {
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
    @CollectionTable(name = "court_day_working", joinColumns = @JoinColumn(name = "court_id"))
    @Column(name = "day_working")
    private Set<DayInWeek> dayWorking;


    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private boolean status;

}
