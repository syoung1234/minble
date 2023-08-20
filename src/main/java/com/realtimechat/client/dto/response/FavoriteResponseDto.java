package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteResponseDto {
    private long favoriteCount;
    private boolean like;

    public FavoriteResponseDto(long favoriteCount, boolean like) {
        this.favoriteCount = favoriteCount;
        this.like = like;
    }
}
