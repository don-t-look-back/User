package com.example.dlbuser.auth.constant;

import lombok.Getter;

@Getter
public enum TokenType {

    JWT("JWT"),
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");


    private final String message;

    TokenType(String message) {
        this.message = message;
    }
}