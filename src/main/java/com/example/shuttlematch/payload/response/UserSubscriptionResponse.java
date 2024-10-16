package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserSubscription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSubscriptionResponse {

    private Long id;
    private Long userId;
    private Subscription subscription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;

    public UserSubscriptionResponse(UserSubscription userSubscription) {
        id = userSubscription.getId();
        userId = userSubscription.getUser().getId();
        subscription = userSubscription.getSubscription();
        startDate = userSubscription.getStartDate();
        endDate = userSubscription.getEndDate();
        active = userSubscription.isActive();
    }
}
