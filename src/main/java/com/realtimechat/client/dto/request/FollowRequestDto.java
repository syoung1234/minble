package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FollowRequestDto {
    private Member follower;
    private Member following;
    private String nickname;

    @Builder
    public FollowRequestDto(Member follower, Member following, String nickname) {
        this.nickname = nickname;
        this.follower = follower;
        this.following = following;
    }

    public void setFollower(Member follower) {
        this.follower = follower;
    }

    public void setFollowing(Member following) {
        this.following = following;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Follow toEntity() {
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
    
}
