package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.realtimechat.client.domain.Reply;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReplyResponseDto {
    private String nickname;
    private String profilePath;
    private String content;
    private String createdAt;

    public ReplyResponseDto(Reply entity) {
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

}
