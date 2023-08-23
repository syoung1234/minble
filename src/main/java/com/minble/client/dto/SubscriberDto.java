package com.minble.client.dto;

import java.time.LocalDateTime;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;
import com.minble.client.domain.Subscriber;

import lombok.Builder;
import lombok.Getter;


@Getter
public class SubscriberDto {
    private Member member;
    private ChatRoom chatRoom;
    private LocalDateTime expiredAt;
    private Boolean status;
    private String customerUid;

    @Builder
    public SubscriberDto(Member member, ChatRoom chatRoom, LocalDateTime expiredAt, String customerUid) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.expiredAt = expiredAt;
        this.status = true;
        this.customerUid = customerUid;
    }

    public Subscriber toEntity() {
        return Subscriber.builder()
            .member(member)
            .chatRoom(chatRoom)
            .expiredAt(expiredAt)
            .status(true)
            .customerUid(customerUid)
            .build();
    }
}
