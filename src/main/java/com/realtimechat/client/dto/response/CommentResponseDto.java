package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Reply;

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
    private Long replyCount;
    private List<Reply> replyList;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        // this.replyList = entity.getReplyList();
    }

    public void setReplyCount(Long count) {
        this.replyCount = count;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
