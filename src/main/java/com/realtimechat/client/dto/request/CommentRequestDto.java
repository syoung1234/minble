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
public class CommentRequestDto {
    private String content;
    private Integer postId;
    private Integer parentId;
    private Integer depth;

    @Builder
    public CommentRequestDto(String content, Integer postId, Integer parentId, Integer depth) {
        this.content = content;
        this.postId = postId;
        this.parentId = parentId;
        this.depth = depth;
    }

    public Comment toEntity(Member member, Post post, Comment parent) {
        return Comment.builder()
            .member(member)
            .post(post)
            .content(content)
            .parent(parent)
            .depth(depth)
            .build();
    }
    
}
