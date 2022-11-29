package com.realtimechat.client.repository;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
