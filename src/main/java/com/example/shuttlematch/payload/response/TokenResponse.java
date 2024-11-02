package com.example.shuttlematch.payload.response;

import com.example.shuttlematch.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class TokenResponse {
    private long userId;
    private String email;
    private Set<Role> role;
    private String accessToken;

    public TokenResponse(long userId, String email, Set<Role> role, String accessToken){
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.accessToken = accessToken ;
    }
}
