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

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.profilePath = comment.getMember().getProfilePath();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.replyCount = comment.getChildren().size();
    }

}

