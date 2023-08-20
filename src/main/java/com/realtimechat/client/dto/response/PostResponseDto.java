package com.realtimechat.client.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;

import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PostResponseDto {
    private String nickname;
    private String profilePath;
    private int postId;
    private String content;
    private String createdAt;
    private long commentCount;
    private long favoriteCount;
    private List<PostFileResponseDto> postFileList;
    private boolean favorite;

    public void setPostFileList(List<PostFileResponseDto> postFileList) {
        this.postFileList = postFileList;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public PostResponseDto(String nickname, String profilePath, int postId, String content,
                           LocalDateTime createdAt, long commentCount, long favoriteCount) {
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.commentCount = commentCount;
        this.favoriteCount = favoriteCount;
    }

}
