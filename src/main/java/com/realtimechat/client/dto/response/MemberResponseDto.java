package com.realtimechat.client.dto.response;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberResponseDto {
    private String nickname;
    private String profilePath;

    public MemberResponseDto(String nickname, String profilePath) {
        this.nickname = nickname;
        this.profilePath = profilePath;
    }
}
