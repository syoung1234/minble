package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FollowRequestDto {
    private Member member;
    private Member following;
    private String nickname;

    @Builder
    public FollowRequestDto(String nickname) {
        this.nickname = nickname;
    }

    public Follow toEntity(Member member, Member following) {
        return Follow.builder()
                .member(member)
                .following(following)
                .build();
    }
    
}
