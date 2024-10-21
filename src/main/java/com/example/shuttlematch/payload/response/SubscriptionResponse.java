package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {
    private Long id;
    private String name;
    private double price;
    private int durationDays;
    private int likesPerDay;
    private Status status;

    public SubscriptionResponse(Subscription subscription) {
        this.id = subscription.getId();
        this.name = subscription.getName();
        this.price = subscription.getPrice();
        this.durationDays = subscription.getDurationDays();
        this.likesPerDay = subscription.getLikesPerDay();
        this.status = subscription.getStatus();
    }
}
