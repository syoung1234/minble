package com.realtimechat.client.repository;

import com.realtimechat.client.domain.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    
}
