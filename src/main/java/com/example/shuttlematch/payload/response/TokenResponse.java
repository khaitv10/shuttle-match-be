package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenResponse {
    private long userId;
    private String email;
    private String accessToken;

    public TokenResponse(long userId, String email, String accessToken){
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken ;
    }
}
