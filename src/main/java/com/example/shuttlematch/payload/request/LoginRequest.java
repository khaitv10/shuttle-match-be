package com.example.shuttlematch.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    private String email;
    //@ToString.Exclude //ẩn pass
    private String password;
}
