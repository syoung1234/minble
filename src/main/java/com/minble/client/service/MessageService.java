package com.minble.client.service;

import java.time.LocalDateTime;
import java.util.List;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;
import com.minble.client.domain.Message;
import com.minble.client.domain.MessageFile;
import com.minble.client.domain.Subscriber;
import com.minble.client.dto.request.MessageRequestDto;
import com.minble.client.dto.response.MessageDetailResponseDto;
import com.minble.client.dto.response.MessageResponseDto;
import com.minble.client.repository.ChatRoomRepository;
import com.minble.client.repository.MemberRepository;
import com.minble.client.repository.MessageFileRepository;
import com.minble.client.repository.MessageRepository;
import com.minble.client.repository.SubscriberRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
    
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SubscriberRepository subscriberRepository;
    private final MessageRepository messageRepository;
    private final MessageFileRepository messageFileRepository;

    public MessageResponseDto get(Member member, String nickname) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        List<MessageDetailResponseDto> messageDetailResponse;

        // 스타
        Member publisher = memberRepository.findByNickname(nickname).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findByMember(publisher);

        // 구독자
        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        // TEST - TEST 끼리 접근 가능
        if (member.getRole().toString().equals("ROLE_SUBSCRIBER_TEST")) {
            if (!publisher.getRole().toString().equals("ROLE_STAR_TEST")) {
                return null;
            }
        } else if (subscriber == null || subscriber.getExpiredAt().isBefore(LocalDateTime.now())) { // 구독자가 아니거나 구독 기간이 끝났다면 접근 불가
            // publisher 가 아닐 경우
            if (!member.getNickname().equals(nickname)) {
                return null;
            }
        }

        // 스타가 접속했을 경우 
        if (publisher.getNickname().equals(member.getNickname())) {
            messageDetailResponse = messageRepository.findByChatRoomOrderByCreatedAtAsc(chatRoom);
        } else { // 구독자가 접속했을 경우
            messageDetailResponse = messageRepository.findAllByMemberOrMemberAndChatRoomOrderByCreatedAtAsc(publisher, member, chatRoom);
        }

        messageResponseDto.setChannel(chatRoom.getChannel());
        messageResponseDto.setProfilePath(member.getProfilePath());
        messageResponseDto.setNickname(member.getNickname());
        messageResponseDto.setMessageList(messageDetailResponse);


        return messageResponseDto;
    }

    public Message save(MessageRequestDto messageRequestDto) {
        Member member = memberRepository.findByNickname(messageRequestDto.getNickname()).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findByChannel(messageRequestDto.getChannel());
        MessageFile messageFile = messageFileRepository.findByFilePath(messageRequestDto.getFilePath()).orElse(null);

        messageRequestDto.setMember(member);
        messageRequestDto.setChatRoom(chatRoom);
        messageRequestDto.setMessageFile(messageFile);

        Message message = messageRepository.save(messageRequestDto.toEntity());
        
        return message;
    }
}
