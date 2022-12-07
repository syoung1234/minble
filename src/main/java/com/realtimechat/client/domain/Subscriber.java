package com.realtimechat.client.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Subscriber extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    // 0: 구독 취소 1: 구독 중 
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean status;

    private LocalDateTime expiredAt;
    
    @Builder
    public Subscriber(Member member, ChatRoom chatRoom, LocalDateTime expiredAt) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.expiredAt = expiredAt;
    }
}
