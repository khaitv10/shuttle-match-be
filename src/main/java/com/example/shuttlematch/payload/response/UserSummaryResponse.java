package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserPhoto;
import com.example.shuttlematch.enums.Level;
import com.example.shuttlematch.enums.Time;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
public class UserSummaryResponse {
    private long id;
    private String fullName;
    private String age;
    private String description;
    private String location;
    private Level level;
    private Set<Time> availableTime;
    private Set<String> photos;

    public UserSummaryResponse(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.age = calculateAge(user.getDob());
        this.description = user.getDescription();
        this.location = user.getLocation();
        this.level = user.getLevel();
        this.availableTime = user.getAvailableTime();
        this.photos = user.getUserPhotos().stream()
                .map(UserPhoto::getPhotoUrl)
                .collect(Collectors.toSet());
    }

    private String calculateAge(LocalDate dob) {
        if (dob == null) {
            return "";
        }
        return String.valueOf(Period.between(dob, LocalDate.now()).getYears());
    }
}


