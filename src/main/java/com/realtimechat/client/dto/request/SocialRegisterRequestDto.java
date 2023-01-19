package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialRegisterRequestDto {
    private String email;
    private String nickname;
    private String social;

    public SocialRegisterRequestDto(String email, String nickname, String social) {
        this.email = email;
        this.nickname = nickname;
        this.social = social;
    }

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .nickname(nickname)
            .social(social)
            .build();
    }
    
}
