package com.realtimechat.client.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageResponseDto {
    private String nickname;
    private String channel;
    private String profilePath;
    private List<MessageDetailResponse> messageList;

    public MessageResponseDto(String nickname, String channel, String profilePath, List<MessageDetailResponse> messageList) {
        this.nickname = nickname;
        this.channel = channel;
        this.profilePath = profilePath;
        this.messageList = messageList;
    }
   
}
