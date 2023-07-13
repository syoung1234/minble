package com.realtimechat.client.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MemberException extends RuntimeException {
    private final MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
