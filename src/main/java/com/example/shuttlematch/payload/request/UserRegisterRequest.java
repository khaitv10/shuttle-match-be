package com.example.shuttlematch.payload.request;


import com.example.shuttlematch.enums.Level;
import com.example.shuttlematch.enums.Time;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Valid
public class UserRegisterRequest {
    @Email(message="Invalid email address")
    private String email;

    @NotBlank(message="Phone number cannot be blank")
    @Pattern(regexp="^[0-9]{10,11}$", message="Invalid phone number")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp="^(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must be at least 6 characters long, containing at least one uppercase letter and one digit")
    private String password;

    @NotBlank(message="Name cannot be blank")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Day of birth cannot be blank")
    private LocalDate dob;

    @NotNull(message="Gender cannot be blank")
    //@NotNull cho các kiểu dữ liệu tham chiếu (object) như String hoặc các kiểu dữ liệu như enum,
    private String gender;

    private String occupation;

    @NotNull(message="Level cannot be blank")
    private Level level;

    private String description;

    @NotNull(message="Location cannot be blank")
    private String location;

    private Set<Time> availableTime;

    @NotNull(message="Photo cannot be blank")
    private Set<String> photo;

}
