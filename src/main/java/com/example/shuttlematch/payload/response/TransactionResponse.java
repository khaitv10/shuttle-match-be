package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Long userId;
    private TransactionType transactionType;
    private Long amount;
    private Status status;
    private String referenceCode;
    private String code;
    private LocalDateTime transactionDate;


    public TransactionResponse(Transaction transaction) {
        id = transaction.getId();
        userId = transaction.getUser().getId();
        transactionType = transaction.getTransactionType();
        amount = transaction.getAmount();
        status = transaction.getStatus();
        referenceCode = transaction.getReferenceCode();
        code = transaction.getCode();
        transactionDate = transaction.getTransactionDate();
    }
}
