package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "code")
    private String code;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
}
