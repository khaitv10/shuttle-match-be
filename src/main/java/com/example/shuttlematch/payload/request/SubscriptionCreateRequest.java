package com.example.shuttlematch.payload.request;

import com.example.shuttlematch.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SubscriptionCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 15, message = "Name must not exceed 15 characters")
    private String name;

    @NotNull(message = "Price cannot be blank")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Duration cannot be blank")
    @Min(value = 0, message = "Duration must be at least 0 day")
    @Max(value = 30, message = "Duration must not exceed 30 days")
    private Integer durationDays;

    @NotNull(message = "Likes per day cannot be blank")
    private Integer likesPerDay;
}
