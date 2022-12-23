package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.realtimechat.client.domain.Comment;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentResponseDto {
    private Integer id;
    private String nickname;
    private String profilePath;
    private String content;
    private String createdAt;
    private int replyCount;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public void setReplyCount(int count) {
        this.replyCount = count;
    }

   
}

