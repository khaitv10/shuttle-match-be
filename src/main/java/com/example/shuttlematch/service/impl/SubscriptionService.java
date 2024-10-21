package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.SwipeType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.SubscriptionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.SubscriptionRepository;
import com.example.shuttlematch.service.ISubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService implements ISubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    @Override
    public ApiResponse<List<SubscriptionResponse>> getAll() {
        try {
            List<Subscription> subscriptionList = subscriptionRepository.findAll();
            if(subscriptionList.isEmpty()) throw new BusinessException(ResponseCode.FAILED);

            List<SubscriptionResponse> listRes = subscriptionList.stream()
                    .map(SubscriptionResponse::new)
                    .toList();

            return new ApiResponse<>(ResponseCode.SUCCESS, listRes);
        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}
