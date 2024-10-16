package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserSubscription;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.SwipeType;
import com.example.shuttlematch.enums.TransactionType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.UserSubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.SubscriptionRepository;
import com.example.shuttlematch.repository.TransactionRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.repository.UserSubscriptionRepository;
import com.example.shuttlematch.service.IUserSubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserSubscriptionService implements IUserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public ApiResponse<UserSubscriptionResponse> updateUserSubscription(long userId, long transactionId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                    () -> new BusinessException(ResponseCode.TRANSACTION_NOT_FOUND)
            );

            UserSubscription userSubscription = userSubscriptionRepository.findByUserId(userId).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_SUBSCRIPTION_NOT_FOUND)
            );

            if (transaction.getTransactionType().equals(TransactionType.MEMBERSHIP)) {
                user.setDiamondMember(true);
                user.setLikeRemaining(10);
                user.setUpdatedAt(LocalDateTime.now());
                
                userSubscription.setSubscription(subscriptionRepository.findById(2));
                userSubscription.setStartDate(LocalDateTime.now());
                userSubscription.setEndDate(LocalDateTime.now().plusMonths(1));

                userRepository.save(user);
                userSubscriptionRepository.save(userSubscription);
            } else if (transaction.getTransactionType().equals(TransactionType.LIKE)) {
                user.setLikeRemaining(user.getLikeRemaining() + 5);
                user.setUpdatedAt(LocalDateTime.now());

                userSubscription.setSubscription(subscriptionRepository.findById(4));
                userSubscription.setStartDate(LocalDateTime.now());
                userSubscription.setEndDate(LocalDateTime.now().plusDays(1));
                userRepository.save(user);
                userSubscriptionRepository.save(userSubscription);
            }

            UserSubscriptionResponse response = new UserSubscriptionResponse(userSubscription);
            return new ApiResponse<>(ResponseCode.SUCCESS, response);
        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}













