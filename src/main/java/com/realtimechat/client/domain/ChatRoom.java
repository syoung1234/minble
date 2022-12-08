package com.realtimechat.client.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String channel;
    
    public void update(String channel) {
        this.channel = channel;
    }
}
