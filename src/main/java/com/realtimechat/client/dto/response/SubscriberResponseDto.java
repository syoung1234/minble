package com.realtimechat.client.dto.response;

import com.realtimechat.client.domain.Subscriber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscriberResponseDto {
    private String nickname;
    private String email;
    private Boolean status;

    public SubscriberResponseDto(Subscriber entity) {
        this.nickname = entity.getMember().getNickname();
        this.email = entity.getMember().getEmail();
        this.status = entity.isStatus();
    }
    
}
