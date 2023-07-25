package com.realtimechat.client.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member Not Found"),
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "Duplicated Member"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "Unauthorized Member"),
    FORBIDDEN_MEMBER(HttpStatus.FORBIDDEN, "Forbidden Member"),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post Not Found");

    private final HttpStatus httpStatus;
    private final String message;
}
