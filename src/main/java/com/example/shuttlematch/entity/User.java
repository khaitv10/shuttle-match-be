package com.example.shuttlematch.entity;

import com.example.shuttlematch.enums.Level;
import com.example.shuttlematch.enums.Role;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.Time;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "users")
@Accessors(chain=true)
public class User implements Serializable , UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = true, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate dob;

    private String occupation;

    private String gender;

    private String otp;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String description;

    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "available_time", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "available_time")
    private Set<Time> availableTime;

    @Column(name = "diamond_member", nullable = false)
    private boolean diamondMember;

    @Column(name = "like_remaining")
    private int likeRemaining = 5;

    @Column(name = "report_count")
    private int reportCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ElementCollection(targetClass=Role.class,fetch = FetchType.EAGER)  //  specify where JPA can find information about the Enum.
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="role_user") // create the table that hold relationship from Person to InterestsEnum
    @Column(name="roles") // Column name in person_interest
    private Set<Role> role ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserPhoto> userPhotos;

    public User (String email , String password , Set<Role> role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.role.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
