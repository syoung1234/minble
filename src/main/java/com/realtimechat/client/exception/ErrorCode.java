package com.realtimechat.client.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member Not found"),
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "Duplicated Member"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "Unauthorized"),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post Not found");

    private final HttpStatus httpStatus;
    private final String message;
}
