package com.realtimechat.client.dto.response;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private String nickname;
    private String profilePath;
    private String content;
    private long commentCount;
    private long favoriteCount;


    public PostListResponseDto(String nickname, String profilePath, String content, long commentCount, long favoriteCount) {
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.content = content;
        this.commentCount = commentCount;
        this.favoriteCount = favoriteCount;
    }

}
