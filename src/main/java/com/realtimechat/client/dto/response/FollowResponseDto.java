package com.realtimechat.client.dto.response;

import com.realtimechat.client.domain.Member;

import lombok.Getter;

@Getter
public class FollowResponseDto {
    private Member follower;

    public FollowResponseDto(Member follower) {
        this.follower = follower;
    }
    
}
