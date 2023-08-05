package com.realtimechat.client.exception;

import lombok.Getter;

@Getter
public class FollowException extends RuntimeException {
    private final ErrorCode errorCode;

    public FollowException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
