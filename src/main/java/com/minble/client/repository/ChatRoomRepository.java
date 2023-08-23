package com.minble.client.repository;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    ChatRoom findByMember(Member member);
    ChatRoom findByChannel(String channel);
}
