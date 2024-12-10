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
    INVALID_GOOGLE_TOKEN(10010,"Invalid google token"),
    LOGIN_GOOGLE_FAILED(10011,"Login google failed"),
    RESET_PASSWORD(10012,"New password sent to user's email"),



    DATETIME_INVALID(101001,"Date time format invalid"),
    USER_PHOTO_COUNT_INVALID(101002,"User photo number from 2 to 6"),
    REQUIRED_FIELDS_MISSING(101003,"Required fields missing"),
    AGE_NEED_OVER_16(101004,"Your age need over 16"),
    RUN_OUT_OF_LIKE(101005,"You have run out of likes for the day"),
    NOT_HAVE_LIKE(101006,"No one likes you"),
    USER_NOT_MATCH(101007,"User not matched"),
    USER_NOT_HAVE_ANY_MATCH(101007,"User not have any matched"),



            MESSAGE_SENT_SUCCESS(102000,"Message sent successfully"),
            MESSAGE_SENT_FAIl(102001,"Message sent fail"),
            MESSAGE_EMPTY(102002,"Message content not be empty"),


    SUBSCRIPTION_NOT_FOUND(10300,"Subscription not found"),
    USER_SUBSCRIPTION_NOT_FOUND(10301,"User Subscription not found"),
    SUBSCRIPTION_LIKE_PER_DAY(10302,"Like per day must be 5 or 10"),
    SUBSCRIPTION_NAME_EXISTED(10303,"Subscription name already existed"),

    TRANSACTION_NOT_FOUND(10302,"Transaction not found"),
    TRANSACTION_PAID(10303,"Transaction has already been paid"),






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
