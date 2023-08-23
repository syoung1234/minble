package com.minble.client.dto.request;

import com.minble.client.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialRegisterRequestDto {
    private String email;
    private String nickname;
    private String social;
    private String profilePath;

    public SocialRegisterRequestDto(String email, String nickname, String social) {
        this.email = email;
        this.nickname = nickname;
        this.social = social;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .nickname(nickname)
            .profilePath(profilePath)
            .social(social)
            .build();
    }
    
}
