package com.realtimechat.client.dto.request;

import java.time.LocalDateTime;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Subscriber;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriberRequestDto {
    private Member member;
    private ChatRoom chatRoom;
    private LocalDateTime expiredAt;
    private Boolean status;

    @Builder
    public SubscriberRequestDto(Member member, ChatRoom chatRoom, LocalDateTime expiredAt) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.expiredAt = expiredAt;
        this.status = true;
    }

    public Subscriber toEntity() {
        return Subscriber.builder()
            .member(member)
            .chatRoom(chatRoom)
            .expiredAt(expiredAt)
            .status(true)
            .build();
    }
    
}
