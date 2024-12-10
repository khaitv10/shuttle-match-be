package com.example.shuttlematch.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecoverPasswordRequest {
    @NotBlank
    @Email
    private String email;
}
