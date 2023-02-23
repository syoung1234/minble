package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String status;
    private String roleType;
    private String nickname;

    public LoginResponseDto(String accessToken, String refreshToken, String status, String roleType, String nickname) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = status;
        this.roleType = roleType;
        this. nickname = nickname;
    }
    
}
