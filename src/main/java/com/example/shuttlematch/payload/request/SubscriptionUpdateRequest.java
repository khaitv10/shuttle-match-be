package com.example.shuttlematch.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SubscriptionUpdateRequest {
    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Size(max = 15, message = "Name must not exceed 15 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @Min(value = 0, message = "Duration must be at least 0 day")
    @Max(value = 30, message = "Duration must not exceed 30 days")
    private Integer durationDays;

    @Min(value = 0, message = "Like per day must be at least 0 day")
    private Integer likesPerDay;
}
