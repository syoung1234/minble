package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Message;
import com.realtimechat.client.dto.response.MessageDetailResponse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<MessageDetailResponse> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);
    
}
