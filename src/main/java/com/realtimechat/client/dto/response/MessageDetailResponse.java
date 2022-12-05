package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.realtimechat.client.domain.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageDetailResponse {
    private String content;
    private String nickname;
    private String createdAt;
    private String channel;
    private String profilePath;
    
    public MessageDetailResponse(Message entity) {
        this.content = entity.getContent();
        this.nickname = entity.getMember().getNickname();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.channel = entity.getChatRoom().getChannel();
        this.profilePath = entity.getMember().getProfilePath();
    }
}
