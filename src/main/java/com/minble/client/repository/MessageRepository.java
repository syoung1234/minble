package com.minble.client.repository;

import java.util.List;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;
import com.minble.client.domain.Message;
import com.minble.client.dto.response.MessageDetailResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<MessageDetailResponseDto> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);

    List<MessageDetailResponseDto> findAllByMemberOrMemberAndChatRoomOrderByCreatedAtAsc(Member member, Member member2, ChatRoom chatRoom);
    
}
