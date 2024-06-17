package com.example.dlbuser.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleResponse<T> {

    private String message;
    private T data;

    private SimpleResponse(String message) {
        this.message = message;
    }

    private SimpleResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static SimpleResponse<?> from(String message) {

        return new SimpleResponse<>(message);
    }

    public static <T> SimpleResponse<T> of(String message, T data) {

        return new SimpleResponse<T>(message, data);
    }
}