package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequestDto {

    private String content;
    private Member member;

    @Builder
    public PostRequestDto(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public Post toEntity() {
        return Post.builder()
                .content(content)
                .member(member)
                .build();
    }
    
}
