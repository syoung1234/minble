package com.realtimechat.client.dto.response;


import com.realtimechat.client.domain.Member;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberResponseDto {
    private String nickname;
    private String profilePath;

    public MemberResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.profilePath = member.getProfilePath();
    }
}
