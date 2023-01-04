package com.realtimechat.client.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Payment;
import com.realtimechat.client.domain.Subscriber;
import com.realtimechat.client.dto.SubscriberDto;
import com.realtimechat.client.dto.request.PaymentRequestDto;
import com.realtimechat.client.dto.request.SubscriberRequestDto;
import com.realtimechat.client.dto.response.SubscriberResponseDto;
import com.realtimechat.client.repository.ChatRoomRepository;
import com.realtimechat.client.repository.PaymentRepository;
import com.realtimechat.client.repository.SubscriberRepository;
import com.realtimechat.client.util.Iamport;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PaymentRepository paymentRepository;

    @Value("${imp.key}")
    private String imp_key;

    @Value("${imp.secret}")
    private String imp_secret;

    // 페이지 조회
    public SubscriberResponseDto get(Member member, String name) {
        ChatRoom chatRoom = chatRoomRepository.findByChannel(name);
        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        SubscriberResponseDto subscriberResponseDto = new SubscriberResponseDto();

        if (subscriber == null) {
            subscriberResponseDto.setEmail(member.getEmail());
            subscriberResponseDto.setNickname(member.getNickname());
            subscriberResponseDto.setStatus(null);
        } else {
            subscriberResponseDto = new SubscriberResponseDto(subscriber);
        }

        return subscriberResponseDto;
    }


    // 구독
    @Transactional
    public String save(Member member, SubscriberRequestDto subscriberRequestDto) {
        String message = "success";
        String nickname = subscriberRequestDto.getNickname();
        String merchant_uid = member.getNickname() + "_" + LocalDateTime.now();

        ChatRoom chatRoom = chatRoomRepository.findByChannel(nickname);
        Subscriber subscriber = subscriberRepository.findByMemberAndChatRoom(member, chatRoom);

        // 구독 여부
        if (subscriber != null) {
            // 구독 중이거나 구독 취소를 했지만 유효기간이 남았을 경우 
            if (subscriber.isStatus() == true || subscriber.getExpiredAt().isBefore(LocalDateTime.now())) {
                message = "already registered";
                return message;
            }
        }

        Iamport iamport = new Iamport();
        JSONObject body = (JSONObject) iamport.subscriber(imp_key, imp_secret, subscriberRequestDto.getCustomer_uid(), merchant_uid);

        if (body.get("code").equals(0)) {
            System.out.println(body.get("response"));
            ObjectMapper mapper = new ObjectMapper();
            JSONParser jsonParser = new JSONParser();

            try {
                String jsonStr = mapper.writeValueAsString(body.get("response"));
                JSONObject paymentData = (JSONObject) jsonParser.parse(jsonStr);
                String status = paymentData.get("status").toString();
                System.out.println(paymentData);
                String cardName = null;
                if (status.equals("paid")) { // 결제 완료
                    // 결제 저장 
                    PaymentRequestDto paymentRequestDto = new PaymentRequestDto(member, paymentData);

                    if (paymentData.get("card_name") != null) {
                        paymentRequestDto.setCardName(cardName);
                    }
    
                    Payment payment = paymentRepository.save(paymentRequestDto.toEntity());
    
                    // 유효기간 
                    LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);
    
                    if (subscriber == null) { // 생성
                        SubscriberDto subscriberDto = new SubscriberDto(member, chatRoom, expiredAt, subscriberRequestDto.getCustomer_uid());
                        subscriberRepository.save(subscriberDto.toEntity());
                    } else { // 업데이트 
                        subscriber.update(expiredAt);
                    }
    
                    payment.update(subscriber);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            
             
        } else {
            // 결제 실패 
            message = "fail";
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
