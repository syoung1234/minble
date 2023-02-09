package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String status;

    public LoginResponseDto(String accessToken, String status) {
        this.accessToken = accessToken;
        this.status = status;
    }
    
}
