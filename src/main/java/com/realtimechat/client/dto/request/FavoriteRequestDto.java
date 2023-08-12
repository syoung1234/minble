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
public class FavoriteRequestDto {
    private Integer postId;

    public FavoriteRequestDto(Integer postId) {
        this.postId = postId;
    }

    public Favorite toEntity(Member member, Post post) {
        return Favorite.builder()
                .member(member)
                .post(post)
                .build();
    }
    
}
