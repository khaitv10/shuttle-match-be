package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.TransactionType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.PayOSRequest;
import com.example.shuttlematch.payload.response.TransactionResponse;
import com.example.shuttlematch.repository.SubscriptionRepository;
import com.example.shuttlematch.repository.TransactionRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.repository.UserSubscriptionRepository;
import com.example.shuttlematch.service.ITransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.payos.type.CheckoutResponseData;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionService implements ITransactionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final TransactionRepository transactionRepository;
    private final PayOSService payOSService;

    @Override
    public long createUserSubscriptionTransaction(String email, long subscriptionId) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
        );
        Subscription subscription = subscriptionRepository.findById(subscriptionId);
        if (subscription == null) throw new BusinessException(ResponseCode.SUBSCRIPTION_NOT_FOUND);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount((long) subscription.getPrice());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(Status.PENDING);
        if (subscription.getName().equals("DIAMOND")) {
            transaction.setTransactionType(TransactionType.MEMBERSHIP);
        } else if (subscription.getName().equals("IN_DAY")) {
            transaction.setTransactionType(TransactionType.LIKE);
        } else {
            transaction.setTransactionType(null);
        }

        long transactionId = transactionRepository.save(transaction).getId();
        transactionRepository.flush();

        return transactionId;
    }

    @Override
    public String getPaymentUrl(long transactionId, String redirectUrl) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new BusinessException(ResponseCode.TRANSACTION_NOT_FOUND)
        );

        if (transaction.getStatus().equals(Status.COMPLETED))
            throw new BusinessException(ResponseCode.TRANSACTION_PAID);
        PayOSRequest payOSRequest = new PayOSRequest(
                (int) transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionType().toString(),
                redirectUrl,
                redirectUrl
        );

        CheckoutResponseData checkoutResponseData = payOSService.createPaymentUrl(payOSRequest);
        return checkoutResponseData.getCheckoutUrl();
    }

    @Override
    public ApiResponse<TransactionResponse> updateTransaction(long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new BusinessException(ResponseCode.TRANSACTION_NOT_FOUND)
        );

        transaction.setStatus(Status.COMPLETED);
        transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse(transaction);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }


    @Override
    public ApiResponse<List<TransactionResponse>> getAllPaymentUser(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            long userId = user.getId();
            List<Transaction> transactionList = transactionRepository.findAllByUserId(userId);
            if (transactionList.isEmpty()) throw new BusinessException(ResponseCode.TRANSACTION_NOT_FOUND);

            List<TransactionResponse> responseList = transactionList.stream()
                    .filter(transaction -> transaction.getStatus().equals(Status.COMPLETED))
                    .map(TransactionResponse::new)
                    .toList();

            return new ApiResponse<>(ResponseCode.SUCCESS, responseList);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAILED);
        }
    }


}











