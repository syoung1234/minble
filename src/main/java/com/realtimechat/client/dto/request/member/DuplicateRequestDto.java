package com.realtimechat.client.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DuplicateRequestDto {
    private String email;
    private String nickname;

    public DuplicateRequestDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
