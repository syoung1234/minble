package com.minble.client.dto.request;

import com.minble.client.domain.Favorite;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
