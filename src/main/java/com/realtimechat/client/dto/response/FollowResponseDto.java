package com.realtimechat.client.dto.response;


import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class FollowResponseDto {
    private String nickname;
    private String profilePath;

    public FollowResponseDto(Follow entity) {
        this.nickname = entity.getFollowing().getNickname();
        this.profilePath = entity.getFollowing().getProfilePath();
    }

}
