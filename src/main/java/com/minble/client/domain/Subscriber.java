package com.minble.client.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Subscriber extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    // 0: 구독 취소 1: 구독 중 
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean status;

    private LocalDateTime expiredAt;

    @Column(length = 100, unique = true)
    private String customer_uid;
    
    // 수정 
    public void update(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    // 구독 취소
    public void cancel(Boolean status) {
        this.status = status;
    }

    @Builder
    public Subscriber(Member member, ChatRoom chatRoom, LocalDateTime expiredAt, Boolean status, String customerUid) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.expiredAt = expiredAt;
        this.status = true;
        this.customer_uid = customerUid;
    }
}
