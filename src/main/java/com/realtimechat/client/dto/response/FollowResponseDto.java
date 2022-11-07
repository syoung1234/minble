package com.realtimechat.client.dto.response;


import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
@Getter
public class FollowResponseDto {
    private Member member;
    private Member following;

    public FollowResponseDto(Follow entity) {
        this.member = entity.getMember();
        this.following = entity.getFollowing();
    }
    
}
