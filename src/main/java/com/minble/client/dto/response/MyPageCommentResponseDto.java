package com.minble.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.minble.client.domain.Comment;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyPageCommentResponseDto {
    private String content;
    private String createdAt;
    private Integer postId;

    public MyPageCommentResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.postId = entity.getPost().getId();
    }
    
}
