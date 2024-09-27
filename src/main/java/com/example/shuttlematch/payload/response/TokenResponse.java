package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenResponse {
    private String email;
    private String accessToken;

    public TokenResponse(String email, String accessToken){
        this. email = email;
        this. accessToken = accessToken ;
    }
}
