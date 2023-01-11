package com.realtimechat.client.service.Admin;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.MessageRequestDto;
import com.realtimechat.client.dto.request.admin.AdminMemberRequestDto;
import com.realtimechat.client.dto.response.admin.AdminMemberResponseDto;
import com.realtimechat.client.repository.ChatRoomRepository;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.repository.MessageRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    // admin 회원관리
    public Page<AdminMemberResponseDto> list(Pageable pageable) {
        Page<Member> memberList = memberRepository.findAll(pageable);

        Page<AdminMemberResponseDto> adminMemberResponseDto = memberList.map(member -> new AdminMemberResponseDto(member));

        return adminMemberResponseDto;
    }

    // admin 회원관리 상세 
    public AdminMemberResponseDto get(String nickname) {
        Member member = memberRepository.findByNickname(nickname);

        AdminMemberResponseDto adminMemberResponseDto = new AdminMemberResponseDto(member);

        return adminMemberResponseDto;
    }

   
}
