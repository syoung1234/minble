package com.realtimechat.client.service;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.MyPageRequestDto;
import com.realtimechat.client.repository.ChatRoomRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;

    // 닉네임 변경
    public String updateNickname(Member member, String nickname) {
        String message = "success";
        Member m = memberRepository.findByNickname(nickname);
        if (m != null) {
            message = "fail";
        } else {
            Member updateMember = memberRepository.findByNickname(member.getNickname());

            // role type - star 일 경우 channel명 변경
            if (updateMember.getRole().equals(Role.ROLE_STAR)) {
                ChatRoom chatRoom = chatRoomRepository.findByChannel(member.getNickname());
                chatRoom.update(nickname);
                chatRoomRepository.save(chatRoom);
            }
            updateMember.updateNickname(nickname);
            memberRepository.save(updateMember);
        }
        
        
        return message;
    }

    // 비밀번호 변경
    public String updatePassword(Member member, MyPageRequestDto myPageRequestDto) {
        String message = "success";
        // 비밀번호 맞는지 확인
        if (!passwordEncoder.matches(myPageRequestDto.getPassword(), member.getPassword())) {
            message = "fail";
        } else {
            // 비밀번호 변경
            member.updatePassword(passwordEncoder.encode(myPageRequestDto.getNewPassword()));
            memberRepository.save(member);
        }
        return message;
    }
    
}
