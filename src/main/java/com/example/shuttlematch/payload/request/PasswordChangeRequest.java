package com.example.shuttlematch.payload.request;

import com.example.shuttlematch.utils.AppConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class PasswordChangeRequest {
    @NotBlank(message = "Old password must not be blank!")
    private String oldPassword;
    @NotBlank(message = "New password must not be blank!")
    @Pattern(regexp = AppConstants.PASSWORD_REGEX,
            message = "Password must be at least 6 characters long, containing at least one uppercase letter and one digit")
    private String newPassword;
}
