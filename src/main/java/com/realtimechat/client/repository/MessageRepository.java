package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Message;
import com.realtimechat.client.dto.response.MessageDetailResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<MessageDetailResponseDto> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);

    List<MessageDetailResponseDto> findAllByMemberOrMemberAndChatRoomOrderByCreatedAtAsc(Member member, Member member2, ChatRoom chatRoom);
    
}
