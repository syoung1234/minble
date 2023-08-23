package com.minble.client.dto.response;


import com.minble.client.domain.Follow;

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
