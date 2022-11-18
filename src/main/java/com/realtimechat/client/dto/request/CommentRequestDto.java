package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {
    private Member member;
    private Post post;
    private String content;
    private Integer postId;

    @Builder
    public CommentRequestDto(Member member, Post post, String content, Integer postId) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.postId = postId;
    }

    public Comment toEntity() {
        return Comment.builder()
            .member(member)
            .post(post)
            .content(content)
            .build();
    }
    
}
