package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FavoriteRequestDto {
    private Member member;
    private Post post;
    private Integer PostId;

    @Builder
    public FavoriteRequestDto(Member member, Integer postId) {
        this.member = member;
        this.PostId = postId;
    }

    public Favorite toEntity() {
        return Favorite.builder()
                .member(member)
                .post(post)
                .build();
    }
    
}
