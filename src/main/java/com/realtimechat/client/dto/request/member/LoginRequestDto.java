package com.realtimechat.client.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
