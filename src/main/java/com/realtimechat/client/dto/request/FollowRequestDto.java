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
    public FollowRequestDto(Member member, Member following, String nickname) {
        this.member = member;
        this.following = following;
        this.nickname = nickname;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFollowing(Member following) {
        this.following = following;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Follow toEntity() {
        return Follow.builder()
                .member(member)
                .following(following)
                .build();
    }
    
}
