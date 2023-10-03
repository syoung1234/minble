package com.minble.client.config;

import com.minble.client.exception.ErrorResponse;
import com.minble.client.exception.FollowException;
import com.minble.client.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException exception) {
        log.error("MemberException ", exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode());

        return new ResponseEntity<>(errorResponse, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(FollowException.class)
    public ResponseEntity<ErrorResponse> handleFollowException(FollowException exception) {
        log.error("FollowException", exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode());

        return new ResponseEntity<>(errorResponse, exception.getErrorCode().getHttpStatus());
    }
}
