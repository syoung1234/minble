package com.realtimechat.client.service;

import java.time.LocalDateTime;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Subscriber;
import com.realtimechat.client.dto.request.SubscriberRequestDto;
import com.realtimechat.client.repository.ChatRoomRepository;
import com.realtimechat.client.repository.SubscriberRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final ChatRoomRepository chatRoomRepository;
    
    // 구독
    public String save(Member member, String nickname) {
        String message = "success";
        ChatRoom chatRoom = chatRoomRepository.findByChannel(nickname);
        LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        if (subscriber == null) { // 생성
            SubscriberRequestDto subscriberRequestDto = new SubscriberRequestDto(member, chatRoom, expiredAt);
            subscriberRepository.save(subscriberRequestDto.toEntity());
        } else { // 업데이트 
            if (subscriber.isStatus() == true) {
                message = "already registered";
            } else {
                subscriber.update(expiredAt);
            }
            
        }
        
        return message;
    }

    // 구독 취소
    public String cancel(Member member, String nickname) {
        String message = "success";
        ChatRoom chatRoom = chatRoomRepository.findByChannel(nickname);
        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        subscriber.cancel(false);

        return message;
    }
}
