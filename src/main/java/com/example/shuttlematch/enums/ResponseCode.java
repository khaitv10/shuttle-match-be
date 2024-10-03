package com.example.shuttlematch.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(0,"Success"),
    FAILED(1,"Failed"),
    ENCRYPT_FAILED(2, "Encrypt_failed"),

    USER_NOT_FOUND(10001,"User not found"),
    USER_EMAIL_EXISTED(10002,"User email existed"),
    USER_REGISTER_FAILED(10003,"User register failed"),
    USER_EMAIL_OR_PASSWORD_INCORRECT(10004, "Email or password incorrect"),
    USER_BANNED_AND_INACTIVE(10005,"User is banned"),
    USER_CHECK_OTP_FAILED(10006,"OTP incorrect"),
    USER_DO_NOT_PERMISSION(10007,"Do not permission"),
    USER_PHONE_EXISTED(10008,"User phone existed"),

    PASSWORD_NOT_FOUND(10009,"Password is wrong"),
    INVALID_GOOGLE_TOKEN(10009,"Invalid google token"),
    LOGIN_GOOGLE_FAILED(10009,"Login google failed"),



    DATETIME_INVALID(101001,"Date time format invalid"),
    USER_PHOTO_COUNT_INVALID(101002,"User photo number from 2 to 6"),
    REQUIRED_FIELDS_MISSING(101003,"Required fields missing"),
    AGE_NEED_OVER_16(101004,"Your age need over 16"),



    FILM_EXISTED(102001,"Film existed"),
    CREATE_FILM_FAILED(102002, "Create film failed"),
    FILM_NOT_FOUND(102003,"Film not found"),
    FILM_DELETED(102004,"Film deleted"),

    WRONG_RATING(200001, "Rating from 1 - 5"),


    ;

    private int code;
    private String message;

    ResponseCode(int code, String message){
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
