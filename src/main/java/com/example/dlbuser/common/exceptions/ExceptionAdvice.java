package com.example.dlbuser.common.exceptions;

import com.example.dlbuser.common.response.BaseResponse;
import com.example.dlbuser.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> ExceptionHandle(HttpClientErrorException exception) {

        return ResponseEntity.status(exception.getStatusCode())
                .body(new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR, exception.getMessage()));
    }
}
