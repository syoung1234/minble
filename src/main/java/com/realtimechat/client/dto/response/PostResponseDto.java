package com.realtimechat.client.dto.response;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Integer id;
    private Member member;
    private String content;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.content = entity.getContent();
    }
    
}
