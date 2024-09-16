package com.example.shuttlematch.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@ToString
public enum DayInWeek {
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");

    private final String day;

//    public static void isValid(DayInWeek dayInWeek) {
//        if (dayInWeek == null) {
//            throw new ApiException(HttpStatus.BAD_REQUEST, "Day is not valid");
//        }
//        for (DayInWeek s : DayInWeek.values()) {
//            if (s.day.equals(dayInWeek.name())) {
//                return;
//            }
//        }
//        throw new ApiException(HttpStatus.BAD_REQUEST, "Day is not valid");
//    }
}
