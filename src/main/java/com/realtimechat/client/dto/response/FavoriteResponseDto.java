package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteResponseDto {
    private long count;
    private boolean like;

    public FavoriteResponseDto(long count, boolean like) {
        this.count = count;
        this.like = like;
    }
}
