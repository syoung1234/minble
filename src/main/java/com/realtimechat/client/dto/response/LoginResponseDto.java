package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String status;
    private String roleType;
    private String nickname;

    public LoginResponseDto(String accessToken, String status, String roleType, String nickname) {
        this.accessToken = accessToken;
        this.status = status;
        this.roleType = roleType;
        this. nickname = nickname;
    }
    
}
