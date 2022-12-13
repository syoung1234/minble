package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyRequestDto {
    private Member member;
    private Comment comment;
    private String content;
    private Integer commentId;
    
    @Builder
    public ReplyRequestDto(Member member, Comment comment, String content) {
        this.member = member;
        this.comment = comment;
        this.content = content;
    }

    public Reply toEntity() {
        return Reply.builder()
            .member(member)
            .comment(comment)
            .content(content)
            .build();
    }
}
