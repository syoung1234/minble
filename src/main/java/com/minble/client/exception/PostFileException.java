package com.minble.client.exception;

import lombok.Getter;

@Getter
public class PostFileException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostFileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
