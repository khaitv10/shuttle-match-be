package com.example.shuttlematch.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum Reason {

    INAPPROPRIATE_PHOTOS("Inappropriate Photos"),
    HARASSMENT("Harassment"),
    IMPERSONATION("Impersonation"),
    ABUSIVE_OR_HOSTILE_LANGUAGE("Abusive or hostile language");

    private final String reason;

}


