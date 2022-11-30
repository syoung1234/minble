package com.realtimechat.client.service;

import java.time.LocalDateTime;
import java.util.List;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Subscriber;
import com.realtimechat.client.dto.request.MessageRequestDto;
import com.realtimechat.client.dto.response.MessageDetailResponse;
import com.realtimechat.client.dto.response.MessageResponseDto;
import com.realtimechat.client.repository.ChatRoomRepository;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.repository.MessageRepository;
import com.realtimechat.client.repository.SubscriberRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
    
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SubscriberRepository subscriberRepository;
    private final MessageRepository messageRepository;

    public MessageResponseDto get(Member member, String nickname) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        // 스타
        Member publisher = memberRepository.findByNickname(nickname);
        ChatRoom chatRoom = chatRoomRepository.findByMember(publisher);

        // 구독자
        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        // 구독자가 아니거나 구독 기간이 끝났다면 접근 불가
        if (subscriber == null || subscriber.getExpiredAt().isBefore(LocalDateTime.now()) == true) {
            return null;
        }

        List<MessageDetailResponse> messageDetailResponse = messageRepository.findByChatRoomOrderByIdAsc(chatRoom);

        messageResponseDto.setChannel(chatRoom.getChannel());
        messageResponseDto.setProfilePath(member.getProfilePath());
        messageResponseDto.setNickname(member.getNickname());
        messageResponseDto.setMessageList(messageDetailResponse);


        return messageResponseDto;
    }

    public void save(MessageRequestDto messageRequestDto) {
        Member member = memberRepository.findByNickname(messageRequestDto.getNickname());
        ChatRoom chatRoom = chatRoomRepository.findByChannel(messageRequestDto.getChannel());

        messageRequestDto.setMember(member);
        messageRequestDto.setChatRoom(chatRoom);

        messageRepository.save(messageRequestDto.toEntity());
        
    }
}
