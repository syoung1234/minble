package com.minble.client.dto.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMemberRequestDto {

    private String nickname;
    private String role;

    public AdminMemberRequestDto(String nickname, String role) {
        this.nickname = nickname;
        this.role = role;
    }
    
}
