package com.example.shuttlematch.payload.response;


import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserPhoto;
import com.example.shuttlematch.enums.Level;
import com.example.shuttlematch.enums.Role;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.Time;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private LocalDate dob;
    private String occupation;
    private String gender;
    private Level level;
    private String description;
    private String location;
    private Set<Time> availableTime;
    private boolean diamondMember;
    private int likeRemaining;
    private int reportCount;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Role> role;
    private Set<String> photos;

    public UserResponse(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.dob = user.getDob();
        this.occupation = user.getOccupation();
        this.gender = user.getGender();
        this.level = user.getLevel();
        this.description = user.getDescription();
        this.location = user.getLocation();
        this.availableTime = user.getAvailableTime();
        this.diamondMember = user.isDiamondMember();
        this.likeRemaining = user.getLikeRemaining();
        this.reportCount = user.getReportCount();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.role = user.getRole();

        this.photos = user.getUserPhotos().stream()
                .map(UserPhoto::getPhotoUrl)
                .collect(Collectors.toSet());
    }

}
