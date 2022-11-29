package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageResponseDto {
    private String nickname;
    private String channel;
    private String profilePath;
}
