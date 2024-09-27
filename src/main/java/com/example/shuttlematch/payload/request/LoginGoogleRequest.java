package com.example.shuttlematch.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginGoogleRequest {
    @NotBlank
    private String token;
}
