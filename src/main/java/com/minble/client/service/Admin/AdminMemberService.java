package com.minble.client.service.Admin;

import com.minble.client.domain.ChatRoom;
import com.minble.client.domain.Member;
import com.minble.client.domain.Role;
import com.minble.client.dto.request.MessageRequestDto;
import com.minble.client.dto.request.admin.AdminMemberRequestDto;
import com.minble.client.dto.response.admin.AdminMemberResponseDto;
import com.minble.client.repository.ChatRoomRepository;
import com.minble.client.repository.MemberRepository;
import com.minble.client.repository.MessageRepository;

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
    public Page<AdminMemberResponseDto> list(String searchType, String keyword, Pageable pageable) {
        Page<Member> memberList;
        if (searchType == null) {
            memberList = memberRepository.findAll(pageable);
        } else {
            if (searchType.equals("nickname")) { // 닉네임 검색
                memberList = memberRepository.findByNicknameContaining(keyword, pageable);
            } else if (searchType.equals("email")) { // 이메일 검색
                memberList = memberRepository.findByEmailContaining(keyword, pageable);
            } else if (searchType.equals("role")) { // 회원유형 검색
                Role role;
                if (keyword.equals("관리자")) {
                    role = Role.ROLE_ADMIN;
                } else if (keyword.equals("스타")) {
                    role = Role.ROLE_STAR;
                } else if (keyword.equals("구독자")) {
                    role = Role.ROLE_SUBSCRIBER;
                } else {
                    role = Role.ROLE_MEMBER;
                }
                memberList = memberRepository.findByRole(role, pageable);
            } else {
                memberList = memberRepository.findAll(pageable);
            }
        }

        Page<AdminMemberResponseDto> adminMemberResponseDto = memberList.map(member -> new AdminMemberResponseDto(member));

        return adminMemberResponseDto;
    }

    // admin 회원관리 상세 
    public AdminMemberResponseDto get(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElse(null);

        AdminMemberResponseDto adminMemberResponseDto = new AdminMemberResponseDto(member);

        return adminMemberResponseDto;
    }

    // admin 회원 role 변경
    public String update(AdminMemberRequestDto adminMemberRequestDto) {
        String nickname = adminMemberRequestDto.getNickname();
        String roleName = adminMemberRequestDto.getRole();
        String message = "success";

        Member member = memberRepository.findByNickname(nickname).orElse(null);

        Role role;

        if (roleName.equals("관리자")) {
            role = Role.ROLE_ADMIN;
        } else if (roleName.equals("스타")) {
            role = Role.ROLE_STAR;
        } else if (roleName.equals("구독자")) {
            role = Role.ROLE_SUBSCRIBER;
        } else {
            role = Role.ROLE_MEMBER;
        }

        member.updateRole(role);
        memberRepository.save(member);

        if (roleName.equals("스타")) {
            // chatRoom 생성 + 메시지 1개 생성
            ChatRoom chatRoom = ChatRoom.builder().member(member).channel(nickname).build();
            chatRoomRepository.save(chatRoom);

            String content = "안녕하세요. " + nickname + " 구독자만 사용 가능한 공간입니다.";
            MessageRequestDto messageRequestDto = new MessageRequestDto(member, chatRoom, content);
            messageRepository.save(messageRequestDto.toEntity());

        }

        return message;
    }
    
}
