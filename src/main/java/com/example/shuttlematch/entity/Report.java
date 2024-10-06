package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.Reason;
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
@Table(name = "reports")
@Accessors(chain=true)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "reported_by", referencedColumnName = "id")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "reported_user", referencedColumnName = "id")
    private User reportedUser;

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Column(name = "report_time")
    private LocalDateTime reportTime;
}
