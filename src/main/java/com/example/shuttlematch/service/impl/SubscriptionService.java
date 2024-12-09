package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.SwipeType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionCreateRequest;
import com.example.shuttlematch.payload.request.SubscriptionUpdateRequest;
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

    @Override
    public ApiResponse<SubscriptionResponse> createSubscription(SubscriptionCreateRequest request) {
        try {
            String name = request.getName();
            if (name != null) {
                name = name.trim().toUpperCase().replace(" ", "_");
            }

            if (subscriptionRepository.existsByName(name)) {
                throw new BusinessException(ResponseCode.SUBSCRIPTION_NAME_EXISTED);
            }
            if (request.getLikesPerDay() != 5 && request.getLikesPerDay() != 10) {
                throw new BusinessException(ResponseCode.SUBSCRIPTION_LIKE_PER_DAY);
            }

            Subscription subscription = new Subscription()
                    .setName(name)
                    .setPrice(request.getPrice())
                    .setDurationDays(request.getDurationDays())
                    .setLikesPerDay(request.getLikesPerDay())
                    .setStatus(Status.ACTIVE);

            subscriptionRepository.save(subscription);
            SubscriptionResponse response = new SubscriptionResponse(subscription);
            return new ApiResponse<>(ResponseCode.SUCCESS, response);

        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }

    @Override
    public ApiResponse<SubscriptionResponse> updateSubscription(SubscriptionUpdateRequest request) {
        try {
            Subscription subscription = subscriptionRepository.findById(request.getId()).orElseThrow(
                    () -> new BusinessException(ResponseCode.SUBSCRIPTION_NOT_FOUND)
            );

            if(request.getName() != null) {
                String name = request.getName();
                if (name != null) {
                    name = name.trim().toUpperCase().replace(" ", "_");
                }

                if (subscriptionRepository.existsByName(name)) {
                    throw new BusinessException(ResponseCode.SUBSCRIPTION_NAME_EXISTED);
                }
                subscription.setName(request.getName());
            }

            if(request.getLikesPerDay() != null) {
                if (request.getLikesPerDay() != 5 && request.getLikesPerDay() != 10) {
                    throw new BusinessException(ResponseCode.SUBSCRIPTION_LIKE_PER_DAY);
                }
                subscription.setLikesPerDay(request.getLikesPerDay());
            }

            if(request.getPrice() != null) {
                subscription.setPrice(request.getPrice());
            }

            if(request.getDurationDays() != null) {
                subscription.setDurationDays(request.getDurationDays());
            }


            subscriptionRepository.save(subscription);
            SubscriptionResponse response = new SubscriptionResponse(subscription);
            return new ApiResponse<>(ResponseCode.SUCCESS, response);

        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }

    @Override
    public ApiResponse<SubscriptionResponse> deleteSubscription(Long id) {
        try {
            Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                    () -> new BusinessException(ResponseCode.SUBSCRIPTION_NOT_FOUND)
            );

            subscription.setStatus(Status.INACTIVE);

            subscriptionRepository.save(subscription);
            SubscriptionResponse response = new SubscriptionResponse(subscription);
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
