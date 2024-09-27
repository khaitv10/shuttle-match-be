package com.example.shuttlematch.payload.request;

import com.example.shuttlematch.enums.Level;
import com.example.shuttlematch.enums.Time;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserUpdateRequest {
    @Hidden
    @JsonIgnore
    private Long id;

    private String fullName;

    @Pattern(regexp="^[0-9]{10,11}$", message="Invalid phone number")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String occupation;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String description;
    private String location;

    @Enumerated(EnumType.STRING)
    private Set<Time> availableTime;

    private Set<String> photo;

}
