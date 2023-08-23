package com.minble.client.repository;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;
import com.minble.client.domain.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
